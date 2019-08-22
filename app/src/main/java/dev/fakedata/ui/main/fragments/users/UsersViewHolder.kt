package dev.fakedata.ui.main.fragments.users

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dev.fakedata.App
import dev.fakedata.R
import dev.fakedata.model.UserInfo


class UsersViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.users_list_item, parent, false)
) {
    val ivPhoto = itemView.findViewById<ImageView>(R.id.iv_photo)
    val tvName = itemView.findViewById<TextView>(R.id.tv_name)
    val tvOccupation = itemView.findViewById<TextView>(R.id.tv_occupation)
    var userInfo: UserInfo? = null

    /**
     * Items might be null if they are not paged in yet. PagedListAdapter will re-bind the
     * ViewHolder when Item is loaded.
     */
    fun bindTo(userInfo: UserInfo?) {
        this.userInfo = userInfo

        Glide.with(App.context)
            .load(userInfo?.photoUrl)
            .apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.ic_person)
            .into(ivPhoto);

        tvName.text = userInfo?.firstName + " " + userInfo?.lastName
        tvOccupation.text = userInfo?.jobTitle
    }
}