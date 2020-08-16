package com.example.android_three_line_diary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_three_line_diary.R
import com.example.android_three_line_diary.common.DataParseObject
import com.example.android_three_line_diary.data.Diary
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class ListAdapter(private val listener: OnItemClickListener): RecyclerView.Adapter<ListAdapter.DiaryViewHolder>() {

    private var diaryList = emptyList<Diary>()

    inner class DiaryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        return DiaryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false))
    }

    override fun getItemCount(): Int {
        return diaryList.size
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        val currentItem = diaryList[position]
        holder.itemView.diary_view.text = DataParseObject.dateToStringParse(currentItem.date) + " の日記"

    }

    fun setData(diary: List<Diary>) {
        this.diaryList = diary
        notifyDataSetChanged()
    }

    fun getData(position: Int): Diary {
        return this.diaryList[position]
    }

}
