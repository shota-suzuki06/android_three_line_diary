package com.example.android_three_line_diary.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_three_line_diary.R
import com.example.android_three_line_diary.adapter.DeleteListAdapter
import com.example.android_three_line_diary.common.DataParseObject
import com.example.android_three_line_diary.data.DiaryViewModel
import kotlinx.android.synthetic.main.fragment_delete.view.*

class DeleteFragment : Fragment(), DeleteListAdapter.OnItemClickListener {

    private lateinit var mDiaryViewModel: DiaryViewModel
    private lateinit var adapter: DeleteListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_delete, container, false)

        adapter = DeleteListAdapter(this)
        val recyclerView = view.recyclerviewDel
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mDiaryViewModel = ViewModelProvider(this).get(DiaryViewModel::class.java)
        mDiaryViewModel.readAllDeleteData.observe(viewLifecycleOwner, Observer { diary ->
            adapter.setData(diary)
        })

        return view
    }

    override fun onItemClick(position: Int) {
        val diary = adapter.getData(position)
        val builder = AlertDialog.Builder(requireContext())
        val date = DataParseObject.dateToStringParse(diary.date)
        builder.setPositiveButton("復元") { _, _ ->
            diary.deleted = false
            mDiaryViewModel.updateData(diary)
            Toast.makeText(requireContext(),   "$date の日記を復元しました", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("キャンセル") { _, _ -> }
        builder.setNeutralButton("削除") { _, _ ->
            mDiaryViewModel.deleteData(diary)
            Toast.makeText(requireContext(), "$date の日記を完全に削除しました", Toast.LENGTH_SHORT).show()
        }
        builder.setTitle("$date の日記を復元 or 削除")
        builder.setMessage("$date の日記を復元する場合は復元ボタンを、削除する場合は削除ボタンを押してください。(ゴミ箱で削除した場合は復元できません)")
        builder.create().show()
    }

}