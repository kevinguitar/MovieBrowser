package com.kevingt.moviebrowser.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.WindowManager
import android.widget.Toast
import com.kevingt.moviebrowser.R

abstract class BaseActivity : AppCompatActivity() {
    protected companion object {
        enum class Animation {
            DEFAULT,
            SLIDE_VERTICAL,
            SLIDE_HORIZONTAL
        }
    }

    private lateinit var toast: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView(savedInstanceState)
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun initView(savedInstanceState: Bundle?)

    /**
     * @param toolbar           Custom toolbar (v7)
     * @param titleId           Title's resource id
     * @param showBackButton    Control displayHomeAsUp button's visibility
     */
    protected fun setActionBar(toolbar: Toolbar, @StringRes titleId: Int, showBackButton: Boolean) {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setTitle(titleId)
            setDisplayHomeAsUpEnabled(showBackButton)
        }
    }

    /**
     * @param enable    Enable UI interaction or not.
     */
    protected fun setUiInteraction(enable: Boolean) {
        if (enable) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        }
    }

    /**
     * @param resId     Message's resource id
     * @param duration  The time that the toast will showing
     */
    protected fun showToast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
        if (!::toast.isInitialized) {
            toast = Toast.makeText(this, resId, duration)
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
    protected fun addFragment(
        containerId: Int,
        fragment: Fragment,
        animation: Animation = Animation.DEFAULT,
        data: Bundle? = null
    ) {
        if (data != null) {
            fragment.arguments = data
        }
        supportFragmentManager.beginTransaction().run {
            setFragmentAnimation(this, animation)
            add(containerId, fragment, fragment.javaClass.canonicalName)
            commit()
        }
    }

    /**
     * @param containerId    The target id of ViewGroup to display fragment
     * @param fragment       The fragment should be added
     * @param addToBackStack true if the fragment should be added to backstack
     * @param backStackName  The backstack's name for the fragment
     * @param data           Optional bundle things brought into the fragment
     */
    protected fun replaceFragment(
        containerId: Int, fragment: Fragment, addToBackStack: Boolean = true,
        backStackName: String? = fragment::class.java.canonicalName, data: Bundle? = null
    ) {
        if (data != null) {
            fragment.arguments = data
        }
        supportFragmentManager.beginTransaction().run {
            setFragmentAnimation(this, Animation.SLIDE_HORIZONTAL)
            replace(containerId, fragment, backStackName ?: fragment.javaClass.canonicalName)
            if (addToBackStack) {
                addToBackStack(backStackName ?: fragment.javaClass.canonicalName)
            }
            commit()
        }
    }

    /**
     * @param popToFragment     The target fragment's upper one, all above it will be popped.
     * @return true if the target fragment exists and fragments above it will be popped.
     */
    protected fun popFragment(popToFragment: String? = null): Boolean {
        val fragmentManager = supportFragmentManager
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
     * @param containerId    The target id of ViewGroup to display fragment
     * @param fragment       The fragment should show in the container
     */
    protected fun hideCurrentAndShowFragment(containerId: Int, fragment: Fragment) {
        val currentFragment = getVisibleFragment()
        if (currentFragment == null) {
            addFragment(containerId, fragment)
            return
        }
        supportFragmentManager.beginTransaction().run {
            hide(currentFragment)

            var addedFragment = findFragmentByTag(fragment.javaClass.canonicalName)
            if (addedFragment == null || !fragment.isAdded) {
                add(containerId, fragment, fragment.javaClass.canonicalName)
                addedFragment = fragment
            }
            if (addedFragment.isVisible) {
                return
            }
            show(addedFragment)
            commit()
        }
    }

    /**
     * @return The fragment that show currently
     */
    protected fun getVisibleFragment(): Fragment? {
        supportFragmentManager.fragments.onEach {
            if (it.isVisible) return it
        }
        return null
    }

    /**
     * @param tag   The fragment's canonicalName
     */
    protected fun findFragmentByTag(tag: String): Fragment? = supportFragmentManager.findFragmentByTag(tag)

    /**
     * @param transaction   Current fragment transition which need animation
     * @param animation     The animation type that was define in enum
     */
    private fun setFragmentAnimation(transaction: FragmentTransaction, animation: Animation) {
        when (animation) {
            Animation.DEFAULT ->
                transaction.setCustomAnimations(0, R.anim.slide_out_to_left)
            Animation.SLIDE_VERTICAL ->
                transaction.setCustomAnimations(R.anim.slide_in_from_bottom, R.anim.slide_out_to_bottom)
            Animation.SLIDE_HORIZONTAL ->
                transaction.setCustomAnimations(
                    R.anim.slide_in_from_right,
                    R.anim.slide_out_to_left,
                    R.anim.slide_in_from_left,
                    R.anim.slide_out_to_right
                )
        }
    }

}