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
}