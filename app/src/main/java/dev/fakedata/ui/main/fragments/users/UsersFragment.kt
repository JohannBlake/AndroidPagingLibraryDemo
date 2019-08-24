package dev.fakedata.ui.main.fragments.users

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.fakedata.R
import dev.fakedata.databinding.FragmentUsersBinding
import dev.fakedata.di.components.DaggerUsersFragmentComponent
import dev.fakedata.model.UserInfo
import dev.fakedata.ui.main.fragments.users.adapter.ClickListener
import dev.fakedata.ui.main.fragments.users.adapter.UsersAdapter
import javax.inject.Inject

class UsersFragment : Fragment() {

    companion object {
        fun newInstance() = UsersFragment()
    }

    init {
        DaggerUsersFragmentComponent
            .builder()
            .build()
            .inject(this)
    }

    @Inject
    lateinit var usersViewModelFactory: UsersViewModelFactory

    private lateinit var mUsersViewModel: UsersViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton

    private val clickListener: ClickListener = this::onListItemClicked
    private var recyclerViewAdapter = UsersAdapter(clickListener)

    private var mContext: Context? = null
    private var mUseEndlessScrolling = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mContext = context

        val binding = FragmentUsersBinding.inflate(inflater, container, false)

        recyclerView = binding.rvUsers
        fab = binding.fab

        fab.setOnClickListener { view ->
            mUseEndlessScrolling = !mUseEndlessScrolling
            recyclerViewAdapter = UsersAdapter(clickListener)
            recyclerView.adapter = recyclerViewAdapter
            mUsersViewModel.apiOptions.startPos = 0

            if (mUseEndlessScrolling) {
                fab.setImageResource(R.drawable.ic_all_inclusive)
                mUsersViewModel.onLoadDataFromServer.removeObservers(mContext as LifecycleOwner)

                mUsersViewModel.apiOptions.useFacePhotos = false
                mUsersViewModel.getUsersFromServer()

                mUsersViewModel.onLoadDataFromServer.observe(this, Observer { users ->
                    users?.let {
                        render(users)
                    }
                })

            } else {
                fab.setImageResource(R.drawable.ic_vertical_align_bottom)
                mUsersViewModel.onLoadDataFromLocalDB.removeObservers(mContext as LifecycleOwner)

                mUsersViewModel.apiOptions.useFacePhotos = true
                mUsersViewModel.getUsersFromLocalDB()

                mUsersViewModel.onLoadDataFromLocalDB.observe(this, Observer { users ->
                    users?.let {
                        render(users)
                    }
                })
            }
        }

        mUsersViewModel = ViewModelProviders.of(this, usersViewModelFactory).get(UsersViewModel::class.java)

        setupRecyclerView()

        mUsersViewModel.getUsersFromLocalDB()
        mUsersViewModel.onLoadDataFromLocalDB.observe(this, Observer { users ->
            users?.let {
                render(users)
            }
        })

        return binding.root
    }

    private fun render(pageList: PagedList<UserInfo>) {
        recyclerViewAdapter.submitList(pageList)
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = recyclerViewAdapter

        // This prevents the recyclerview from flickering when inserts/updates are done.
        recyclerView.itemAnimator = null
    }

    private fun onListItemClicked(userInfo: UserInfo) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}
