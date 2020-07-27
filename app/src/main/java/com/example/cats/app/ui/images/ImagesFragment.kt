package com.example.cats.app.ui.images

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
import com.example.converter.RealmConverter
import com.example.cats.R
import com.example.cats.app.helpers.ConnectionDetector
import com.example.cats.app.ui.images.image_info.ImageInfoFragment
import com.example.cats.app.ui.images.recycler_view.ImagesAdapter
import com.example.model.CatImage
import com.example.model_realm.RealmBreed
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_images.*

class ImagesFragment : MvpAppCompatFragment(),
    ImagesView {

    private lateinit var realm: Realm
    private lateinit var adapter: ImagesAdapter
    private lateinit var refreshLayout: SwipeRefreshLayout

    @InjectPresenter
    lateinit var presenter: ImagesPresenter

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
        presenter.hasInternetConnection = cd.isConnectingToTheInternet(view.context)
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
        addOnScrollListenerRecyclerView(imageRecyclerView, layoutManager)
        changeSpanCount(layoutManager)
    }

    private fun onRefreshPresenter() {
        presenter.onRefresh()
    }

    private fun addOnScrollListenerRecyclerView(
        recyclerView: RecyclerView,
        layoutManager: GridLayoutManager
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
        adapter = ImagesAdapter(
            items,
            object :
                ImagesAdapter.Callback {
                override fun onImageClick(image: CatImage) {

                    val imageInfoFragment =
                        ImageInfoFragment()
                    val args = Bundle()
                    if (image.breed.isNullOrEmpty()) {
                        args.putSerializable(
                            "breed", RealmConverter()
                                .breedFromRealm(RealmBreed())
                        )
                    } else {
                        args.putSerializable("breed", image.breed!![0])
                    }

                    args.putString("url", image.url)
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