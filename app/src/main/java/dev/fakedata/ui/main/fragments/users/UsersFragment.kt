package dev.fakedata.ui.main.fragments.users

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dev.fakedata.R
import dev.fakedata.bo.UsersAPIOptions
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
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView

    private val clickListener: ClickListener = this::onListItemClicked
    private val recyclerViewAdapter = UsersAdapter(clickListener)

    private var mContext: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mContext = context

        val binding = FragmentUsersBinding.inflate(inflater, container, false)

        swipeRefresh = binding.swipeRefreshLayout
        recyclerView = binding.rvUsers

        mUsersViewModel = ViewModelProviders.of(this, usersViewModelFactory).get(UsersViewModel::class.java)

        setupRecyclerView()

        mUsersViewModel.getUsersFromLocalDB().observe(this, Observer { users ->
            //mUsersViewModel.totalPageItemsRetrieved(users.size)
            users?.let {
                render(users)
            }
        })

        return binding.root
    }

    private fun render(pageList: PagedList<UserInfo>) {
        recyclerViewAdapter.submitList(pageList)

        if (pageList.isEmpty()) {
            recyclerView.visibility = View.GONE
        } else {
            recyclerView.visibility = View.VISIBLE
        }
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
