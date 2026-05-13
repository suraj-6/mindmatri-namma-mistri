package com.nammamistri.app

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nammamistri.app.data.model.Site
import com.nammamistri.app.databinding.ActivityMainBinding
import com.nammamistri.app.databinding.DialogNewSiteBinding
import com.nammamistri.app.databinding.DialogSiteSelectionBinding
import com.nammamistri.app.ui.site.SiteManagementActivity
import com.nammamistri.app.viewmodel.SharedSiteViewModel
import com.nammamistri.app.viewmodel.SiteViewModel

/**
 * Main Activity with BottomNavigationView and NavHostFragment
 * Shows site selection dialog on first launch
 */
class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    
    // Shared ViewModel for site selection - scoped to this activity
    val sharedSiteViewModel: SharedSiteViewModel by viewModels()
    
    // Site management ViewModel
    val siteViewModel: SiteViewModel by viewModels()
    
    private var siteSelectionDialog: AlertDialog? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupNavigation()
        setupToolbar()
        observeViewModel()
        
        // Handle site selection click - now opens site management
        binding.toolbar.setOnClickListener {
            openSiteManagement()
        }
        
        // Show site selection on first launch
        if (savedInstanceState == null) {
            sharedSiteViewModel.showSiteSelection()
        }
    }
    
    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        
        // Setup bottom navigation with nav controller
        binding.bottomNavigation.setupWithNavController(navController)
        
        // Update toolbar title based on destination
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.toolbar.title = when (destination.id) {
                R.id.calculatorFragment -> getString(R.string.calculator_title)
                R.id.laborFragment -> getString(R.string.team_title)
                R.id.photosFragment -> getString(R.string.photos_title)
                R.id.ratesFragment -> getString(R.string.rates_title)
                else -> getString(R.string.app_name)
            }
        }
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
        }
        
        // Site indicator click to change site
        binding.viewSiteColor.setOnClickListener {
            sharedSiteViewModel.showSiteSelection()
        }
    }
    
    private fun observeViewModel() {
        // Observe selected site to update UI
        siteViewModel.selectedSite.observe(this) { site ->
            if (site != null) {
                binding.textToolbarTitle.text = site.name
                // Set site color indicator
                val siteColors = intArrayOf(
                    0xFF6366F1.toInt(), // Indigo
                    0xFFF59E0B.toInt(), // Amber
                    0xFF10B981.toInt(), // Emerald
                    0xFFEF4444.toInt(), // Red
                    0xFF8B5CF6.toInt(), // Violet
                    0xFF06B6D4.toInt()  // Cyan
                )
                val color = siteColors[(site.id % siteColors.size).toInt()]
                binding.viewSiteColor.setBackgroundColor(color)
            } else {
                binding.textToolbarTitle.text = getString(R.string.app_name)
                binding.viewSiteColor.setBackgroundColor(resources.getColor(R.color.primary, null))
            }
        }
        
        // Observe dialog visibility
        sharedSiteViewModel.showSiteSelectionDialog.observe(this) { show ->
            if (show) {
                showSiteSelectionDialog()
            } else {
                siteSelectionDialog?.dismiss()
            }
        }
        
        // Observe active sites
        sharedSiteViewModel.allActiveSites.observe(this) { sites ->
            // If dialog is showing, update the list
            if (siteSelectionDialog?.isShowing == true) {
                // Will be handled by dialog setup
            }
        }
    }
    
    private fun showSiteSelectionDialog() {
        val dialogBinding = DialogSiteSelectionBinding.inflate(LayoutInflater.from(this))
        
        val sites = sharedSiteViewModel.allActiveSites.value ?: emptyList()
        
        // Setup spinner with sites
        val siteNames = sites.map { it.name }.toMutableList()
        if (siteNames.isEmpty()) {
            siteNames.add(getString(R.string.no_sites))
        }
        
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            siteNames
        )
        dialogBinding.spinnerSites.adapter = adapter
        
        // Pre-select current site if any
        siteViewModel.selectedSite.value?.let { currentSite ->
            val index = sites.indexOfFirst { it.id == currentSite.id }
            if (index >= 0) {
                dialogBinding.spinnerSites.setSelection(index)
            }
        }
        
        // New Site button
        dialogBinding.buttonNewSite.setOnClickListener {
            siteSelectionDialog?.dismiss()
            showNewSiteDialog()
        }
        
        // Build dialog
        siteSelectionDialog = MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.select_site))
            .setView(dialogBinding.root)
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                val selectedIndex = dialogBinding.spinnerSites.selectedItemPosition
                if (sites.isNotEmpty() && selectedIndex in sites.indices) {
                    siteViewModel.selectSite(sites[selectedIndex])
                } else if (sites.isEmpty()) {
                    // No sites, show create dialog
                    showNewSiteDialog()
                }
            }
            .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                // If no site selected, keep showing dialog
                if (!sharedSiteViewModel.hasSiteSelected() && sites.isNotEmpty()) {
                    siteViewModel.selectSite(sites[0])
                }
            }
            .setCancelable(sharedSiteViewModel.hasSiteSelected())
            .create()
        
        siteSelectionDialog?.show()
    }
    
    private fun openSiteManagement() {
        val intent = android.content.Intent(this, SiteManagementActivity::class.java)
        startActivity(intent)
    }
    
    private fun showNewSiteDialog() {
        val dialogBinding = DialogNewSiteBinding.inflate(LayoutInflater.from(this))
        
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.new_site))
            .setView(dialogBinding.root)
            .setPositiveButton(getString(R.string.create_site)) { _, _ ->
                val name = dialogBinding.inputSiteName.text.toString().trim()
                val location = dialogBinding.inputSiteLocation.text.toString().trim()
                
                if (name.isBlank()) {
                    Toast.makeText(this, getString(R.string.please_fill_all_fields), Toast.LENGTH_SHORT).show()
                    showNewSiteDialog()
                    return@setPositiveButton
                }
                
                siteViewModel.createSite(
                    Site(
                        name = name,
                        location = location,
                        startDate = System.currentTimeMillis(),
                        isActive = true
                    )
                )
                Toast.makeText(
                    this,
                    "${getString(R.string.saved_successfully)}: $name",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                // If no site exists, show selection again
                if (!sharedSiteViewModel.hasSiteSelected()) {
                    sharedSiteViewModel.showSiteSelection()
                }
            }
            .setCancelable(sharedSiteViewModel.hasSiteSelected())
            .show()
    }
    
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        siteSelectionDialog?.dismiss()
    }
}
