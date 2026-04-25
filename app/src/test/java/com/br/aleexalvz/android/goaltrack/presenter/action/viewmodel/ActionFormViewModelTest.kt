package com.br.aleexalvz.android.goaltrack.presenter.action.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkResponse
import com.br.aleexalvz.android.goaltrack.data.model.response.ActionResponse
import com.br.aleexalvz.android.goaltrack.data.model.response.ActionsResponse
import com.br.aleexalvz.android.goaltrack.domain.model.action.ActionFrequencyEnum
import com.br.aleexalvz.android.goaltrack.domain.model.action.ActionModel
import com.br.aleexalvz.android.goaltrack.domain.repository.ActionRepository
import com.br.aleexalvz.android.goaltrack.presenter.action.model.ActionFormAction
import com.br.aleexalvz.android.goaltrack.presenter.action.model.ActionFormEvent
import com.br.aleexalvz.android.goaltrack.presenter.home.navigation.HomeRoutes.ACTION_ID_ARG
import com.br.aleexalvz.android.goaltrack.presenter.home.navigation.HomeRoutes.GOAL_ID_ARG
import java.time.LocalDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ActionFormViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `submit in create mode emits created event with id returned by api`() = runTest {
        val createdAction = actionResponse(id = 42L, goalId = 7L)
        val repository = FakeActionRepository(
            createActionResponse = NetworkResponse.Success(createdAction)
        )
        val viewModel = ActionFormViewModel(
            actionRepository = repository,
            savedStateHandle = SavedStateHandle(mapOf(GOAL_ID_ARG to 7L))
        )

        viewModel.onUIAction(ActionFormAction.UpdateTitle("Read docs"))
        viewModel.onUIAction(ActionFormAction.UpdateDescription("Study create flow"))
        viewModel.onUIAction(ActionFormAction.UpdateFrequency(ActionFrequencyEnum.DAILY))

        val eventDeferred = async { viewModel.event.first() }

        viewModel.onUIAction(ActionFormAction.Submit)
        advanceUntilIdle()

        val event = eventDeferred.await()

        assertEquals(ActionFormEvent.Created(42L), event)
        assertEquals(42L, viewModel.state.value.id)
        assertEquals(7L, viewModel.state.value.goalId)
        assertTrue(viewModel.state.value.isEditMode)
        assertEquals(1, repository.createCalls.size)
        assertEquals(0, repository.updateCalls.size)
    }

    @Test
    fun `submit in edit mode keeps update flow and emits persisted action id`() = runTest {
        val existingAction = actionResponse(id = 15L, goalId = 9L, title = "Existing title")
        val updatedAction = actionResponse(id = 15L, goalId = 9L, title = "Updated title")
        val repository = FakeActionRepository(
            getActionByIdResponse = NetworkResponse.Success(existingAction),
            updateActionResponse = NetworkResponse.Success(updatedAction)
        )
        val viewModel = ActionFormViewModel(
            actionRepository = repository,
            savedStateHandle = SavedStateHandle(
                mapOf(
                    ACTION_ID_ARG to 15L,
                    GOAL_ID_ARG to 9L
                )
            )
        )

        advanceUntilIdle()
        viewModel.onUIAction(ActionFormAction.UpdateTitle("Updated title"))

        val eventDeferred = async { viewModel.event.first() }

        viewModel.onUIAction(ActionFormAction.Submit)
        advanceUntilIdle()

        val event = eventDeferred.await()

        assertEquals(ActionFormEvent.Created(15L), event)
        assertEquals(0, repository.createCalls.size)
        assertEquals(1, repository.updateCalls.size)
        assertEquals(15L, repository.updateCalls.single().id)
        assertEquals(9L, repository.updateCalls.single().goalId)
    }

    private fun actionResponse(
        id: Long,
        goalId: Long,
        title: String = "Title",
        description: String? = "Description",
        frequency: String = ActionFrequencyEnum.ONCE.name
    ) = ActionResponse(
        id = id,
        goalId = goalId,
        title = title,
        description = description,
        frequency = frequency,
        creationDate = LocalDate.of(2026, 1, 1).toString(),
        endDate = null
    )

    private class FakeActionRepository(
        private val getActionByIdResponse: NetworkResponse<ActionResponse> = NetworkResponse.Failure(
            IllegalStateException("Not configured")
        ),
        private val createActionResponse: NetworkResponse<ActionResponse> = NetworkResponse.Failure(
            IllegalStateException("Not configured")
        ),
        private val updateActionResponse: NetworkResponse<ActionResponse> = NetworkResponse.Failure(
            IllegalStateException("Not configured")
        )
    ) : ActionRepository {

        val createCalls = mutableListOf<ActionModel>()
        val updateCalls = mutableListOf<ActionModel>()

        override suspend fun getActionsByGoal(goalId: Long): NetworkResponse<ActionsResponse> {
            return NetworkResponse.Failure(UnsupportedOperationException())
        }

        override suspend fun getActionById(id: Long): NetworkResponse<ActionResponse> {
            return getActionByIdResponse
        }

        override suspend fun createAction(action: ActionModel): NetworkResponse<ActionResponse> {
            createCalls += action
            return createActionResponse
        }

        override suspend fun updateAction(action: ActionModel): NetworkResponse<ActionResponse> {
            updateCalls += action
            return updateActionResponse
        }

        override suspend fun deleteActionById(id: Long): NetworkResponse<Unit> {
            return NetworkResponse.Failure(UnsupportedOperationException())
        }
    }
}
