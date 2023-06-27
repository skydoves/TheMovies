import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.junit.Assert

class MoviesSteps {

    @Given("^I am on the main movies list screen$")
    fun iAmOnMainMoviesListScreen() {
        // Add any setup code here if needed
    }

    @When("^I tap on a movie$")
    fun iTapOnAMovie() {
        onView(withId(R.id.movies_list)).perform(click())
    }

    @Then("^I should see the movie details screen$")
    fun iShouldSeeTheMovieDetailsScreen() {
        onView(withId(R.id.movie_details)).check(matches(isDisplayed()))
    }

    @When("^I apply a year filter$")
    fun iApplyAYearFilter() {
        onView(withId(R.id.year_filter_button)).perform(click())
        // Assume the year filter dialog is displayed, select a specific year
        onView(withId(R.id.year_filter_dialog)).perform(click())
    }

    @Then("^I should see only movies from the selected year$")
    fun iShouldSeeOnlyMoviesFromSelectedYear() {
        onView(withId(R.id.movies_list)).check { view, _ ->
            // Custom assertions on the filtered movies
            val movieList = view as RecyclerView
            val adapter = movieList.adapter as MovieListAdapter
            for (i in 0 until adapter.itemCount) {
                val movie = adapter.getItem(i)
                Assert.assertTrue(movie.year == selectedYear)
            }
        }
    }

    @When("^I apply a popularity filter$")
    fun iApplyAPopularityFilter() {
        onView(withId(R.id.popularity_filter_button)).perform(click())
        // Assume the popularity filter dialog is displayed, select a specific popularity range
        onView(withId(R.id.popularity_filter_dialog)).perform(click())
    }

    @Then("^I should see only movies within the specified popularity range$")
    fun iShouldSeeOnlyMoviesWithinSpecifiedPopularityRange() {
        onView(withId(R.id.movies_list)).check { view, _ ->
            // Custom assertions on the filtered movies
            val movieList = view as RecyclerView
            val adapter = movieList.adapter as MovieListAdapter
            for (i in 0 until adapter.itemCount) {
                val movie = adapter.getItem(i)
                Assert.assertTrue(movie.popularity >= minPopularity && movie.popularity <= maxPopularity)
            }
        }
    }
}