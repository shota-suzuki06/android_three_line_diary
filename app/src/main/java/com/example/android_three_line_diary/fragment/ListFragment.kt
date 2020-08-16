package com.example.android_three_line_diary.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_three_line_diary.R
import com.example.android_three_line_diary.adapter.ListAdapter
import com.example.android_three_line_diary.common.DataParseObject
import com.example.android_three_line_diary.data.DiaryViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*
import java.util.*

class ListFragment : Fragment(), ListAdapter.OnItemClickListener {

    private lateinit var mDiaryViewModel: DiaryViewModel
    private lateinit var adapter: ListAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        adapter = ListAdapter(this)
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val helper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                var diary = adapter.getData(position)
                diary.deleted = true
                Toast.makeText(requireContext(), DataParseObject.dateToStringParse(diary.date) + " の日記をゴミ箱へ移しました", Toast.LENGTH_SHORT).show()
                mDiaryViewModel.updateData(diary)
            }
        })
        helper.attachToRecyclerView(recyclerView)

        mDiaryViewModel = ViewModelProvider(this).get(DiaryViewModel::class.java)
        mDiaryViewModel.readAllData.observe(viewLifecycleOwner, Observer { diary ->
            adapter.setData(diary)
        })


        view.fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            findNavController().navigate(R.id.action_ListFragment_to_DeleteFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(position: Int) {
        val diary = adapter.getData(position)
        setFragmentResult("edit_diary", bundleOf(
            "diary_id"    to diary.id.toString(),
            "diary_date"  to dateToLong( diary.date ),
            "diary_bad"   to diary.badContent.toString(),
            "diary_good"  to diary.goodContext.toString(),
            "diary_goal"  to diary.goalContext.toString()
        ))
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }

    fun dateToLong(date: Date?): Long? {
        return date?.let { it.time }
    }

}
