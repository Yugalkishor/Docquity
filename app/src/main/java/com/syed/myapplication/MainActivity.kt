package com.syed.myapplication

import MyEventsAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.syed.myapplication.data.BaseResponseModel
import com.syed.myapplication.network.RetrofitHelper
import com.syed.myapplication.repo.EventDetailRepo
import com.syed.myapplication.repo.EventDetailService
import com.syed.myapplication.utils.FirebasePushNotification
import com.syed.myapplication.utils.RequestType
import com.syed.myapplication.utils.Utils.hideSoftKeyBoard
import com.syed.myapplication.viewmodel.MyEventVM
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.common_action_bar_white_background.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity :BaseActivity() {
    private var isLoading: Boolean = false
    private var adapter: MyEventsAdapter ?= null
    private lateinit var viewModel: MyEventVM
    var currentDate=""
    lateinit var eventList: ArrayList<BaseResponseModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        eventList=ArrayList()
        viewModel = getViewModel()
        viewModel.getEventDetail()
        setObserver()
        initview()
        refresh_layout.setOnRefreshListener {
            refresh_layout.isRefreshing = true
            viewModel.getEventDetail()
        }
        var intentBackgroundservice= Intent(this,FirebasePushNotification::class.java)
        startService(intentBackgroundservice)
    }

    private fun initview() {
        iv_back.setOnClickListener({
            finish()
        })
        tv_map.setOnClickListener({
            val intent = Intent(this@MainActivity, MapActivity::class.java)
            startActivity(intent)
        })
        right_button.setOnClickListener {
            fl_search.visibility=View.VISIBLE
            right_button.visibility=View.INVISIBLE

        }
        ll_cancle.setOnClickListener {
            fl_search.visibility=View.INVISIBLE
            right_button.visibility=View.VISIBLE
            hideSoftKeyBoard(this@MainActivity)
        }
        et_search.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

                // TODO Auto-generated method stub
            }

            private var timer = Timer()
            private val DELAY: Long = 300 // milliseconds
            override fun afterTextChanged(s: Editable) {

                // filter your list from your input
                timer.cancel()
                timer = Timer()
                timer.schedule(
                    object : TimerTask() {
                        override fun run() {
                            // TODO: do what you need here (refresh list)
                            // you will probably need to use runOnUiThread(Runnable action) for some specific actions
                            mHandler.obtainMessage(1).sendToTarget()
                        }
                    },
                    DELAY
                )
            }
        })
    }
    var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            if (et_search.text.toString().trim().equals("")) {
                var search: String = et_search.text.toString().trim()
                viewModel.getEventDetail()
            } else {
                val id: Int = et_search.text.toString().trim().toInt()
                viewModel.searchEvent(id)
            }
        }
    }
    private fun getViewModel(): MyEventVM {
        return ViewModelProvider( this, MyEventVM.Factory(EventDetailRepo(RetrofitHelper.createRetrofitService(EventDetailService::class.java)))
        ).get("", MyEventVM::class.java)
    }
    private fun setObserver() {
        viewModel.eventDetail.observe(this, {
            if (viewModel.requestType == RequestType.NORMAL || viewModel.requestType == RequestType.PULL_TO_REFRESH) {
                refresh_layout.isRefreshing = false
                if (eventList!=null){
                    if (eventList.size>0){
                        eventList.clear()
                    }
                }
                eventList=it
                if (eventList.size > 0) {
                    rcv_event_list.visibility= View.VISIBLE
                    ll_no_event.visibility= View.GONE
                    adapter = this.let { it1 -> MyEventsAdapter(it1, it,  currentDate) }
                    rcv_event_list.adapter = adapter
                }else{
                    rcv_event_list.visibility= View.GONE
                    ll_no_event.visibility= View.VISIBLE
                }
            } else {
                adapter?.list?.addAll(it)
                adapter?.notifyDataSetChanged()
            }
        })
 viewModel.searchData.observe(this, {
            if (viewModel.requestType == RequestType.NORMAL || viewModel.requestType == RequestType.PULL_TO_REFRESH) {
                refresh_layout.isRefreshing = false
                if (eventList!=null){
                    if (eventList.size>0){
                        eventList.clear()
                    }
                }
                eventList.add(it)
                if (eventList.size > 0) {
                    rcv_event_list.visibility= View.VISIBLE
                    ll_no_event.visibility= View.GONE
                    adapter = this.let { it1 -> MyEventsAdapter(it1, eventList,  currentDate) }
                    rcv_event_list.adapter = adapter
                }else{
                    rcv_event_list.visibility= View.GONE
                    ll_no_event.visibility= View.VISIBLE
                }
            } else {
                adapter?.list?.add(it)
                adapter?.notifyDataSetChanged()
            }
        })

        viewModel.error?.observe(this, {
            theme?.let { it1 -> showToast(it) }
        })
        viewModel.showLoader.observe(this,{
            if (it) {
                if (this != null)
                    createProgressDialog(this, null)
            } else {
                dismissDialog()
            }
        })
    }

}