package com.example.cats

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.cats.adapter.ImagesAdapter
import com.example.cats.model.AppDatabase
import com.example.cats.model.Image
import com.example.cats.model.ImageRepository
import com.example.cats.presenters.BasePresenter
import kotlinx.android.synthetic.main.fragment_images.*


class ImagesFragment :  MvpAppCompatFragment(), BaseView  {

    private lateinit var adapter: ImagesAdapter
    private lateinit var placeHolder: View
    private lateinit var refreshLayout: SwipeRefreshLayout

    @InjectPresenter
    lateinit var presenter: BasePresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_images, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        initRefreshLayout()

        /*val wordsDao = ImageRepository(AppDatabase.getDatabase(App.appContext).ImageDao())
        repository =
        allWords = repository.allWords*/

        val db = Room.databaseBuilder(
                App.appContext,
        AppDatabase::class.java, "database-name"
        ).build()

        val imageDao = db.imageDao()
        imageDao.insertAll(Image("111", "example.com", emptyList()))

        presenter.loadImages()
    }

    private fun initRefreshLayout() {
        refreshLayout.setOnRefreshListener {
            onRefreshPresenter()
        }
    }

    private fun initViews() {
        refreshLayout = imageSwipeRefreshLayout
    }

    override fun initRecyclerView(items: ArrayList<Image>) {
        callbackAdapter(items)
        imageRecyclerView.adapter = adapter
        val layoutManager = imageRecyclerView.layoutManager as GridLayoutManager
        addOnScrollListenerRecyclerView(imageRecyclerView, layoutManager, presenter)
        changeSpanCount(layoutManager)
    }

    private fun onRefreshPresenter() {
        presenter.onRefresh()
    }

    private fun addOnScrollListenerRecyclerView(
        recyclerView: RecyclerView,
        layoutManager: GridLayoutManager,
        presenter: BasePresenter
    ) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (layoutManager.findLastVisibleItemPosition() >= recyclerView.adapter!!.itemCount - 2
                    && presenter.loadMoreImages
                ) {
                    presenter.loadImages()
                }
            }
        })
    }

    private fun changeSpanCount(layoutManager: GridLayoutManager) {
        val isPortrait = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        layoutManager.spanCount = if (isPortrait) 2 else 3
    }

    override fun changeRefreshVisibilityState(state: Boolean) {
        refreshLayout.isRefreshing = state
    }

    override fun updateAdapter() {
        adapter.notifyDataSetChanged()
    }

    private fun callbackAdapter(items: ArrayList<Image>) {
        adapter = ImagesAdapter(items, object : ImagesAdapter.Callback {
            override fun onImageClick(image: Image) {

                Toast.makeText(context, "image click", Toast.LENGTH_LONG)
            }
        })
    }
}