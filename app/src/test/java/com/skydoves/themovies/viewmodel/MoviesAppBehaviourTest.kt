import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import com.example.movies.MainActivity
import com.example.movies.R

@RunWith(AndroidJUnit4::class)
@LargeTest
class MoviesAppBehaviourTest {
    
    @Rule
    @JvmField
    var mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun navigateToMovieDetailsScreen() {
        // Perform an action to navigate to the details screen
        Espresso.onView(ViewMatchers.withId(R.id.movies_list))
                .perform(ViewActions.click())

        // Assert that the details screen is displayed
        Espresso.onView(ViewMatchers.withId(R.id.movie_details))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun applyYearFilter() {
        // Perform an action to apply the year filter
        Espresso.onView(ViewMatchers.withId(R.id.year_filter_button))
                .perform(ViewActions.click())

        // Assume the year filter dialog is displayed, select a specific year
        Espresso.onView(ViewMatchers.withId(R.id.year_filter_dialog))
                .perform(ViewActions.click())

        // Assert that the movie list is updated with the filtered results
        Espresso.onView(ViewMatchers.withId(R.id.movies_list))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Assert that the filtered movies meet the specified criteria
        Espresso.onView(ViewMatchers.withId(R.id.movies_list))
            .check { view, _ ->
                // Custom assertions on the filtered movies
                val movieList = view as RecyclerView
                val adapter = movieList.adapter as MovieListAdapter
                for (i in 0 until adapter.itemCount) {
                    val movie = adapter.getItem(i)
                    Assert.assertTrue(movie.year == selectedYear)
                }
            }
    }

    @Test
    fun applyPopularityFilter() {
        // Perform an action to apply the popularity filter
        Espresso.onView(ViewMatchers.withId(R.id.popularity_filter_button))
            .perform(ViewActions.click())

        // Assume the popularity filter dialog is displayed, select a specific popularity range
        Espresso.onView(ViewMatchers.withId(R.id.popularity_filter_dialog))
            .perform(ViewActions.click())

        // Assert that the movie list is updated with the filtered results
        Espresso.onView(ViewMatchers.withId(R.id.movies_list))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Assert that the filtered movies meet the specified criteria
        Espresso.onView(ViewMatchers.withId(R.id.movies_list))
            .check { view, _ ->
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