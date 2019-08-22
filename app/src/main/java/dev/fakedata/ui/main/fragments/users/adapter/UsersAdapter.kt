package dev.fakedata.ui.main.fragments.users.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import dev.fakedata.model.UserInfo
import dev.fakedata.ui.main.fragments.users.UsersViewHolder

typealias ClickListener = (UserInfo) -> Unit

class UsersAdapter(
    private val clickListener: ClickListener
) : PagedListAdapter<UserInfo, UsersViewHolder>(diffCallback) {

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val linkedInConnection = getItem(position)

        with(holder) {
            bindTo(linkedInConnection)
            linkedInConnection?.let {
                itemView.setOnClickListener {
                    clickListener(linkedInConnection)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder =
        UsersViewHolder(parent)

    companion object {
        // This diff callback informs the PagedListAdapter how to compute list differences when new  items arrive.
        private val diffCallback = object : DiffUtil.ItemCallback<UserInfo>() {
            override fun areItemsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean =
                oldItem == newItem
        }
    }
}