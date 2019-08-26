package com.msil.sharedmobility.subscribe.presentation.ui.home.components


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment

import com.msil.sharedmobility.subscribe.presentation.ui.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_vehicle_select.*
import android.app.SearchManager

import android.content.Context
import androidx.appcompat.widget.SearchView

import android.app.Activity
import android.app.Dialog
import android.content.SharedPreferences
import com.msil.sharedmobility.subscribe.R
import android.view.ViewGroup
import android.graphics.drawable.ColorDrawable
import android.widget.RelativeLayout
import android.graphics.Color
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.msil.sharedmobility.subscribe.presentation.base.BaseDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.msil.sharedmobility.data.local.getPreference
import com.msil.sharedmobility.data.local.putPreference
import com.msil.sharedmobility.subscribe.di.module.application.SharedPreferenceModule
import com.msil.sharedmobility.subscribe.presentation.config.SharedPreferenceKeys
import com.msil.sharedmobility.subscribe.presentation.ui.home.adaptor.OnCityClickListeners
import com.msil.sharedmobility.subscribe.presentation.ui.home.adaptor.OtherCityListAdaptor
import com.msil.sharedmobility.subscribe.presentation.ui.home.adaptor.PopularCityGridAdaptor
import com.msil.sharedmobility.subscribe.presentation.ui.home.adaptor.SpacesItemDecoration
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 * Created by @author Vikas Aggarwal
 *
 */
class VehicleSelectFragment : BaseDialogFragment() {

    companion object{
        val TAG = "VehicleSelectFragment"
        @JvmStatic

        fun getInstance(): VehicleSelectFragment {
            return VehicleSelectFragment()
        }
    }
    @Inject
    lateinit var mSharedPreference: SharedPreferences
    //getting vehicle view model here
   /* @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: VehicleSelectViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(VehicleSelectViewModel::class.java)
    }
*/
    //setting normal list adaptopr


    private val cityList = arrayOf("Delhi","Ludhiana","Lucknow","abc","abc1","abc2")
    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null
    val TAG = VehicleSelectFragment::class.java.simpleName


    // Activity result mechanism for settarget functionality

    private fun sendResult(message: String) {

        if (targetFragment == null) {
            return
        }
        mSharedPreference.putPreference(SharedPreferenceKeys.PREF_CITY,message)

        val intent = HomeFragment.newIntent(message)
        targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
        dismiss()
        //findNavController().navigate(R.id.nav_to_home_fragment)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setHasOptionsMenu(false)

        //set
        initObserve()
    }

    private fun initObserve() {
       // observe(viewModel.getFruitList(), ::observeCityList)
    }

    private fun observeCityList(cityList: List<String>) {

        println(cityList)
    }

    override fun onResume() {
        super.onResume()
        //Objects.requireNonNull((Objects.requireNonNull(activity) as AppCompatActivity).supportActionBar)!!.hide()


    }

    override fun onStop() {
        super.onStop()
       // Objects.requireNonNull((Objects.requireNonNull(activity) as AppCompatActivity).supportActionBar)!!.show()
    }
    fun showBackButton() {

    }
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search_menu,menu)
        val searchItem:MenuItem? = menu?.findItem(R.id.action_search)

        val searchManager = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
        }
        if (searchView != null) {
            searchView!!.setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))

            queryTextListener = object : SearchView.OnQueryTextListener {



                override fun onQueryTextChange(newText: String): Boolean {
                    println("onQueryTextChange $newText")

                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    println("onQueryTextSubmit $query")

                    return true
                }

            }
            searchView!!.setOnQueryTextListener(queryTextListener)
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_search ->
                // Not implemented here
                return false
            else -> {
            }
        }
        searchView?.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // the content
        val root = RelativeLayout(activity)
        root.layoutParams =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        // creating the fullscreen dialog
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicle_select, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSearch()
        setUpCityList()
        setUpPopularCityList()
        listeners()

    }
    val cityClickListeners:OnCityClickListeners = object : OnCityClickListeners {
        override fun onCitySelected(view: View,city: String,position:Int) {
            println("city selected $city")
            mSharedPreference.putPreference(SharedPreferenceKeys.PREF_CITY_POSITION,position)
            sendResult(city)
        }
    }
    private fun setUpPopularCityList() {
        val adapter =
            PopularCityGridAdaptor(cityList,activity?.baseContext!!,cityClickListeners)
        //autocomplete_search.threshold = 1; //will start working from first character
        val gridLayoutManager = GridLayoutManager(this.context,3)
        recycle_popular_city.layoutManager = gridLayoutManager
        recycle_popular_city.addItemDecoration(SpacesItemDecoration(50))
        recycle_popular_city.adapter = adapter


        //setting prev city value here
        adapter.setPosition(mSharedPreference.getPreference(SharedPreferenceKeys.PREF_CITY_POSITION,-1))

    }

    private fun setUpCityList() {

        //val progressBar = findViewById(R.id.progressbar) as ProgressBar
        //progressBar.visibility = View.VISIBLE

        val adapter =
            OtherCityListAdaptor(cityList,activity?.baseContext!!,cityClickListeners)
        //autocomplete_search.threshold = 1; //will start working from first character
        val linearLayoutManager = LinearLayoutManager(this.context)
        recycle_other_city.layoutManager = linearLayoutManager
        recycle_other_city.setAdapter(adapter);
    }

    private fun setUpSearch() {
        val adapter =
            object : ArrayAdapter<String>(activity?.baseContext!!, android.R.layout.select_dialog_item, cityList){}
        //autocomplete_search.threshold = 1; //will start working from first character
             autocomplete_search.setAdapter(adapter);
        autocomplete_search.setOnItemClickListener { adapter, view, i, l ->
            sendResult(autocomplete_search.text.toString())
        }
    }

    private fun listeners() {

        backArrow.setOnClickListener {
            dismiss()
        }

    }


}
