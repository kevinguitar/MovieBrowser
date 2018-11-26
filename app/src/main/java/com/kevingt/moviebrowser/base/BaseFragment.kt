package com.kevingt.moviebrowser.base

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.kevingt.moviebrowser.R

abstract class BaseFragment : Fragment() {

    protected companion object {
        enum class FragmentAnimation {
            DEFAULT,
            SLIDE_VERTICAL,
            SLIDE_HORIZONTAL
        }
    }

    protected lateinit var fragmentData: Bundle

    private lateinit var toast: Toast

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context !is Activity) {
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            bindActivity(context, parentFragment)
        }
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            bindActivity(activity, parentFragment)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentData = arguments ?: Bundle()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view, savedInstanceState)
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun bindActivity(activity: Activity? = null, fragment: Fragment? = null)

    protected abstract fun initView(parent: View, savedInstanceState: Bundle?)

    /**
     * @param show  Control the parent actionBar's show/ hide
     */
    protected fun setActionBarVisibility(show: Boolean) {
        if (show) {
            (activity as? AppCompatActivity)?.supportActionBar?.show()
        } else {
            (activity as? AppCompatActivity)?.supportActionBar?.hide()
        }
    }

    /**
     * @param titleId   Title's resource id
     */
    protected fun setActionBarTitle(@StringRes titleId: Int) {
        (activity as? AppCompatActivity)?.supportActionBar?.setTitle(titleId)
    }

    /**
     * @param show  Control the parent actionBar's back button show/ hide
     */
    protected fun setHomeAsUpVisibility(show: Boolean) {
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(show)
    }

    /**
     * @param drawableId    Drawable's id in resources
     */
    protected fun setHomeAsUpIndicator(@DrawableRes drawableId: Int) {
        (activity as? AppCompatActivity)?.supportActionBar?.setHomeAsUpIndicator(drawableId)
    }

    /**
     * @param enable    Enable UI interaction or not.
     */
    protected fun setUiInteraction(enable: Boolean) {
        if (enable) {
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        } else {
            activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    /**
     * @param resId     Message's resource id
     * @param duration  The time that the toast will showing
     */
    protected fun showToast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
        if (activity == null) {
            return
        }
        if (!::toast.isInitialized) {
            toast = Toast.makeText(activity, resId, duration)
            toast.show()
            return
        }
        toast.setText(resId)
        toast.duration = duration
        toast.show()
    }

    /**
     * @param containerId The target id of ViewGroup to display fragment
     * @param fragment    The fragment should be added
     * @param animation   The predefined variable to determine the orientation of animation
     * @param data        Optional bundle things brought into the fragment
     */
    protected fun addFragment(containerId: Int, fragment: Fragment, animation: FragmentAnimation = FragmentAnimation.DEFAULT, data: Bundle? = null) {
        if (data != null) {
            fragment.arguments = data
        }
        var transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction = setFragmentAnimation(transaction, animation)
        transaction.add(containerId, fragment, fragment.javaClass.canonicalName)
        transaction.commit()
    }

    /**
     * @param containerId    The target id of ViewGroup to display fragment
     * @param fragment       The fragment should be added
     * @param addToBackStack true if the fragment should be added to backstack
     * @param backStackName  The backstack's name for the fragment
     * @param data           Optional bundle things brought into the fragment
     */
    protected fun replaceFragment(containerId: Int, fragment: Fragment, addToBackStack: Boolean = true,
                                  backStackName: String? = fragment::class.java.canonicalName, data: Bundle? = null) {
        if (data != null) {
            fragment.arguments = data
        }
        var transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction = setFragmentAnimation(transaction, FragmentAnimation.SLIDE_HORIZONTAL)
        transaction.replace(containerId, fragment, backStackName
                ?: fragment.javaClass.canonicalName)
        if (addToBackStack) {
            transaction.addToBackStack(backStackName ?: fragment.javaClass.canonicalName)
        }
        transaction.commit()
    }

    /**
     * @param popToFragment   The target fragment's upper one, all above it will be popped.
     * @return true if the target fragment exists and fragments above it will be popped.
     */
    protected fun popFragment(popToFragment: String? = null): Boolean {
        val fragmentManager = childFragmentManager
        if (fragmentManager.backStackEntryCount == 0) {
            return false
        }
        if (popToFragment == null) {
            fragmentManager.popBackStack()
            return true
        }
        if (findFragmentByTag(popToFragment) == null) {
            return false
        }
        fragmentManager.popBackStack(popToFragment, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        return true
    }

    /**
     * @param tag   The fragment's canonicalName
     */
    protected fun findFragmentByTag(tag: String): Fragment? = childFragmentManager.findFragmentByTag(tag)

    /**
     * @param transaction   Current fragment transition which need animation
     * @param animation     The animation type that was define in enum
     */
    private fun setFragmentAnimation(transaction: FragmentTransaction, animation: FragmentAnimation): FragmentTransaction {
        when (animation) {
            FragmentAnimation.DEFAULT ->
                transaction.setCustomAnimations(0, R.anim.slide_out_to_left)
            FragmentAnimation.SLIDE_VERTICAL ->
                transaction.setCustomAnimations(R.anim.slide_in_from_bottom, R.anim.slide_out_to_bottom)
            FragmentAnimation.SLIDE_HORIZONTAL ->
                transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right)
        }
        return transaction
    }

}