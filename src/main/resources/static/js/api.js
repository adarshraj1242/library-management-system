/**
 * Horizon Library - Core API & Navigation Module
 * Handles session, logout, and cross-section routing
 */

const sId = localStorage.getItem('patron_id');
const pName = localStorage.getItem('patron_name');

// Initial Load
document.addEventListener('DOMContentLoaded', () => {
    if(document.getElementById('patronName')) {
        document.getElementById('patronName').innerText = pName;
    }
    showSection('dashboardSection');
});

/**
 * Logout and clear session
 */
function logout() { 
    localStorage.clear(); 
    window.location.href = 'index.html'; 
}

/**
 * Main routing for sections
 */
function showSection(id) {
    document.querySelectorAll('.section-container').forEach(c => c.style.display = 'none');
    document.querySelectorAll('.nav-link').forEach(l => l.classList.remove('active'));
    
    const targetSection = document.getElementById(id);
    if(targetSection) targetSection.style.display = 'block';
    
    // Update Sidebar Active state
    const navId = 'nav-' + id.replace('Section', '').toLowerCase();
    if(document.getElementById(navId)) {
        document.getElementById(navId).classList.add('active');
    }
    
    // Refresh data for the specific section
    refreshCurrentSection(id);
}

/**
 * Common fetch utility for error handling
 */
async function apiFetch(url, options = {}) {
    try {
        // Ensure headers object exists and include Content-Type
        if (!options.headers) {
            options.headers = {};
        }
        if (!options.headers['Content-Type'] && options.method && options.method.toUpperCase() === 'POST') {
            options.headers['Content-Type'] = 'application/json';
        }
        
        const response = await fetch(url, options);
        if(!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'API Request Failed');
        }
        return await response.json();
    } catch (error) {
        console.error('API Error:', error);
        // Silently fail or alert as needed
        return null;
    }
}

/**
 * Decision logic for which data to refresh
 */
function refreshCurrentSection(id) {
    switch(id) {
        case 'dashboardSection':
            if(window.loadDashboardData) loadDashboardData();
            break;
        case 'catalogSection':
            if(window.loadCatalogData) loadCatalogData();
            break;
        case 'myissuesSection':
            if(window.loadIssuesData) loadIssuesData();
            break;
        case 'profileSection':
            if(window.loadProfileData) loadProfileData();
            break;
    }
}
