package com.salah.ui.details

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ActivityNavigator
import androidx.navigation.navArgs
import com.google.android.material.chip.Chip
import com.salah.R
import com.salah.base.BaseActivity
import com.salah.common.glide.load
import com.salah.common.utils.*
import com.salah.common.utils.ColorUtils.darken
import com.salah.data.Resource
import com.salah.data.sources.remote.api.ApiClient
import com.salah.databinding.ActivityMovieDetailsBinding
import com.salah.model.Genres
import com.salah.model.MovieDetail
import com.salah.ui.details.viewmodel.MovieDetailsViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import java.text.DecimalFormat
import java.util.*

class MovieDetailsActivity : BaseActivity() {

    private val movieDetailsViewModel: MovieDetailsViewModel by viewModel()

    private lateinit var binding: ActivityMovieDetailsBinding
    private val args: MovieDetailsActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        setupPosterImage()

        movieDetailsViewModel.fetchSingleMovie(args.id.toString())

        lifecycleScope.launch {
            movieDetailsViewModel.singleMovieState.collect {
                handleSingleMovieDataState(it)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun finish() {
        super.finish()
        ActivityNavigator.applyPopAnimationsToPendingTransition(this)
    }

    private fun handleSingleMovieDataState(state: Resource<MovieDetail>) {
        when (state.status) {
            Resource.Status.LOADING -> {
                binding.progressBar.visible()
            }
            Resource.Status.SUCCESS -> {
                binding.progressBar.gone()
                loadMovieData(state.data)
            }
            Resource.Status.ERROR -> {
                binding.progressBar.gone()
                Toast.makeText(this, "Error: ${state.message}", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun loadMovieData(data: MovieDetail?) {
        data?.let {
            binding.collapsingToolbar.title = data.title
            binding.detailDescription.text = data.overview
            binding.companyName.text = data.production_companies.firstOrNull()?.name.orNa()

            binding.runtime.text = if (data.runtime > 0)
                TimeUtils.formatMinutes(this, data.runtime) else getString(R.string.no_data_na)

            binding.year.text = if (data.release_date.isNotEmpty())
                LocalDate.parse(data.release_date).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                    .withLocale(Locale.getDefault())) else getString(R.string.no_release_date)

            binding.website.text = HtmlCompat.fromHtml(
                getString(
                    R.string.visit_website_url_pattern,
                    data.homepage,
                    getString(R.string.visit_website)
                ), HtmlCompat.FROM_HTML_MODE_LEGACY
            )

            binding.website.movementMethod = LinkMovementMethod.getInstance()
            fillGenres(data.genres)

            // Rating
            binding.detailExtraInfo.detailRating.text = if (data.vote_average > 0) data.vote_average.toString() else getString(R.string.no_ratings)
            binding.detailExtraInfo.detailVotes.text = if (data.vote_count > 0) data.vote_count.toString() else getString(R.string.no_data_na)
            binding.detailExtraInfo.detailRevenue.text = getString(R.string.revenue_pattern, DecimalFormat("##.##").format(data.revenue / 1000000.0))
        }
    }
    private fun fillGenres(genres: List<Genres>) {
        for (g in genres) {
            val chip = Chip(this)
            chip.text = g.name
            binding.genresChipGroup.addView(chip)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupPosterImage() {
        postponeEnterTransition()

        binding.ivActivityMovieDetails.transitionName = args.id.toString()
        binding.ivActivityMovieDetails.load(url = ApiClient.POSTER_BASE_URL + args.posterPath, width = 160.dp, height = 160.dp) { color ->
            window?.statusBarColor = color.darken
            binding.collapsingToolbar.setBackgroundColor(color)
            binding.collapsingToolbar.setContentScrimColor(color)
            startPostponedEnterTransition()
        }
    }
}