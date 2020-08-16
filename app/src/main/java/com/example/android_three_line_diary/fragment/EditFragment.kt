package com.example.android_three_line_diary.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android_three_line_diary.R
import com.example.android_three_line_diary.data.Diary
import com.example.android_three_line_diary.data.DiaryViewModel
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.android.synthetic.main.fragment_edit.view.*
import java.text.SimpleDateFormat
import java.util.*

class EditFragment : Fragment() {

    private lateinit var mDiaryViewModel: DiaryViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit, container, false)

        mDiaryViewModel = ViewModelProvider(this).get(DiaryViewModel::class.java)

        view.cancel_btn.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        view.saved_btn.setOnClickListener {
            canAccessToDatabase()
        }

        setFragmentResultListener("edit_diary") { resultKey, bundle ->
            val id = bundle.getString("diary_id")
            val date = fromDate( bundle.getLong("diary_date") )
            val bad = bundle.getString("diary_bad")
            val good = bundle.getString("diary_good")
            val goal = bundle.getString("diary_goal")
            println("$id === $date === $bad === $good === $goal ")

            view.diary_id.text = strToEditable(id)
            view.date_et.text  = strToEditable( dateToStr(date) )
            view.bad_et.text   = strToEditable(bad)
            view.good_et.text  = strToEditable(good)
            view.goal_et.text  = strToEditable( goal )
        }

        return view
    }

    private fun canAccessToDatabase() {
        val editId       = diary_id.text.toString()
        val editDate     = date_et.text.toString()
        val editBadText  = bad_et.text.toString()
        val editGoodText = good_et.text.toString()
        val editGoalText = goal_et.text.toString()

        if (checkInputVal(editBadText, editGoodText, editGoalText) && checkInputDate(editDate)) {
            if (TextUtils.isEmpty(editId)) {
                // 新規登録
                val diary = Diary(0, strToDate(editDate), editBadText, editGoodText, editGoalText)
                mDiaryViewModel.insertDiary(diary)
                Toast.makeText(requireContext(), "3行日記を登録しました", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            } else {
                // 編集
                val diary = Diary(Integer.parseInt(editId) , strToDate(editDate), editBadText, editGoodText, editGoalText)
                mDiaryViewModel.updateData(diary)
                Toast.makeText(requireContext(), "3行日記を更新しました", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            }
        } else {
            Toast.makeText(requireContext(), "未入力は無く、日付は「yyyy/MM/dd」の形式でお願いします。", Toast.LENGTH_LONG).show()
        }

    }

    private fun checkInputVal(badText: String, goodText: String, goalText: String): Boolean {
        return !(TextUtils.isEmpty(badText) && TextUtils.isEmpty(goodText) && TextUtils.isEmpty(goalText))
    }

    private fun checkInputDate(date: String): Boolean {
        val regex = Regex(pattern = "^\\d{4}\\/(0?[1-9]|1[012])\\/(0?[1-9]|[12][0-9]|3[01])\$")
        return regex.containsMatchIn(date)
    }

    private fun strToDate(strDate: String): Date {
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        return sdf.parse(strDate)
    }

    private fun dateToStr(date: Date?): String {
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        return sdf.format(date)
    }

    private fun fromDate(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    private fun strToEditable(value: String?): Editable {
        return Editable.Factory.getInstance().newEditable(value)
    }

}
