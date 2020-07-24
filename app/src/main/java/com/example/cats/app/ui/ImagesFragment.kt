package com.example.cats.app.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.cats.R
import com.example.cats.app.BaseView
import com.example.cats.app.ui.presenters.BasePresenter
import com.example.cats.app.ui.presenters.ConnectionDetector
import com.example.cats.domain.model.CatImage
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_images.*

class ImagesFragment : MvpAppCompatFragment(), BaseView {

    private lateinit var realm: Realm
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
        realm = Realm.getDefaultInstance()
        presenter.realm = realm

        return inflater.inflate(R.layout.fragment_images, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val cd = ConnectionDetector()
        presenter.workInternet = cd.isConnectingToInternet(view.context)
        initViews()
        initRefreshLayout()
    }

    private fun initRefreshLayout() {
        refreshLayout.setOnRefreshListener {
            onRefreshPresenter()
        }
    }

    private fun initViews() {
        refreshLayout = imageSwipeRefreshLayout
    }

    override fun initRecyclerView(items: ArrayList<CatImage>) {
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
                if (layoutManager.findLastVisibleItemPosition() >= recyclerView.adapter!!.itemCount - 2) {
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

    override fun makeToastNoInternet() {
        Toast.makeText(context, "Bad internet connection", Toast.LENGTH_LONG).show()
    }

    override fun updateAdapter() {
        adapter.notifyDataSetChanged()
    }

    private fun callbackAdapter(items: ArrayList<CatImage>) {
        adapter = ImagesAdapter(items, object : ImagesAdapter.Callback {
            override fun onImageClick(image: CatImage) {

                val imageInfoFragment = ImageInfoFragment()
                val args = Bundle()
                val breed = image.getFirstBreed()

                args.putString("url", image.url)
                args.putSerializable("breed", breed)
                imageInfoFragment.arguments = args

                activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.addToBackStack(null)
                    ?.add(R.id.container, imageInfoFragment)
                    ?.commit()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

}