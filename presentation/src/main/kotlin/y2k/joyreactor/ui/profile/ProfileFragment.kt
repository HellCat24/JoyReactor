package y2k.joyreactor.ui.profile

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import joy.reactor.data.enteties.Profile
import y2k.joyreactor.R
import y2k.joyreactor.ServiceInjector
import y2k.joyreactor.common.isVisible
import y2k.joyreactor.presenters.ProfilePresenter
import y2k.joyreactor.ui.base.BaseFragment
import y2k.joyreactor.view.WebImageView

class ProfileFragment : BaseFragment() {

    lateinit var presenter: ProfilePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        var navigationView = view.findViewById(R.id.navigation_view) as NavigationView

        presenter = ServiceInjector.inject(object : ProfilePresenter.View {

            override fun hideProfileMenu() {
                navigationView.visibility = View.GONE
            }

            override fun setProfile(profile: Profile) {
                view.findViewById(R.id.btn_login).visibility = View.GONE
                navigationView.visibility = View.VISIBLE

                var headerView = navigationView.getHeaderView(0);

                (headerView.findViewById(R.id.avatar) as WebImageView).setImage(profile.userImage)
                (headerView.findViewById(R.id.rating) as TextView).text = "" + profile.rating
                (headerView.findViewById(y2k.joyreactor.R.id.stars) as android.widget.RatingBar).rating = profile.stars.toFloat()
                (headerView.findViewById(R.id.txt_profile_name) as TextView).text = "" + profile.userName
            }

            override fun setBusy(isBusy: Boolean) {
                view.findViewById(R.id.progress).isVisible = isBusy
            }
        })

        view.findViewById(R.id.btn_login).setOnClickListener { presenter.openLogin() };

        navigationView.setNavigationItemSelectedListener { menuItem ->
            if (menuItem.isChecked)
                menuItem.isChecked = false
            else
                menuItem.isChecked = true

            when (menuItem.itemId) {
                R.id.favorites -> {
                    presenter.openFavorites("HellCat")
                    true
                }
                R.id.tags -> {
                    presenter.openTags()
                    true
                }
                R.id.messages -> {
                    presenter.openDialogs()
                    true
                }
                R.id.log_out -> {
                    presenter.logout()
                    true
                }
                R.id.secret -> {
                    presenter.openSecretActivity()
                    true
                }
                else -> {
                    Toast.makeText(activity, "Somethings Wrong", Toast.LENGTH_SHORT).show()
                    true
                }
            }
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.loadProfile()
    }
}