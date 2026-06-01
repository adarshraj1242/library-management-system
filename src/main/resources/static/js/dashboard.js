/**
 * Horizon Library - Dashboard Module
 * Handles stats, trending collection, and new arrivals
 */

async function loadDashboardData() {
    const myName = localStorage.getItem('patron_name');
    document.querySelectorAll('.me-name').forEach(el => el.innerText = myName);

    // Load Trending Collection
    const trending = await apiFetch('/api/books/trending');
    if(trending) renderBookGrid(trending, 'trending-grid');

    // Load New Arrivals
    const arrivals = await apiFetch('/api/books/new');
    if(arrivals) renderBookGrid(arrivals, 'new-arrivals-grid');

    // Load Overview Stats
    const issues = await apiFetch('/api/issues?student_id=' + sId);
    if(issues) {
        document.getElementById('stat-borrowed').innerText = issues.length;
        document.getElementById('stat-pending').innerText = issues.filter(i => i.status === 'Issued').length;
    }
}

/**
 * Universal book grid renderer
 */
function renderBookGrid(books, containerId) {
    const container = document.getElementById(containerId);
    if(!container) return;
    
    container.innerHTML = '';
    books.forEach(b => {
        container.innerHTML += `
            <div class="book-card" onclick="showCatalogAndSearch('${b.title}')">
                <img src="${b.cover_path || 'https://via.placeholder.com/150x200?text=No+Cover'}" class="book-card-img">
                <div class="book-title">${b.title}</div>
                <div class="book-author">By ${b.author}</div>
                <div class="flex-between">
                    <span class="category-badge">${b.category}</span>
                    <span style="font-size:0.7rem; font-weight:700; color:#94a3b8;">${b.publication_year || ''}</span>
                </div>
            </div>
        `;
    });
}
