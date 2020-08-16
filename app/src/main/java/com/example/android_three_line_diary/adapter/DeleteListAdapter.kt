package com.example.android_three_line_diary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_three_line_diary.R
import com.example.android_three_line_diary.common.DataParseObject
import com.example.android_three_line_diary.data.Diary
import kotlinx.android.synthetic.main.delete_recyclerview_item.view.*

class DeleteListAdapter(private val listener: DeleteListAdapter.OnItemClickListener): RecyclerView.Adapter<DeleteListAdapter.DeleteDiaryViewHolder>() {

    private var deleteDiaryList = emptyList<Diary>()

    inner class DeleteDiaryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeleteDiaryViewHolder {
        return DeleteDiaryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.delete_recyclerview_item, parent, false))
    }

    override fun getItemCount(): Int {
        return deleteDiaryList.size
    }

    override fun onBindViewHolder(holder: DeleteDiaryViewHolder, position: Int) {
        val currentItem = deleteDiaryList[position]
        holder.itemView.delete_diary_view.text =DataParseObject.dateToStringParse( currentItem.date ) + " の日記"
    }

    fun setData(diary: List<Diary>) {
        this.deleteDiaryList = diary
        notifyDataSetChanged()
    }

    fun getData(position: Int): Diary {
        return this.deleteDiaryList[position]
    }


}