import { getStage } from './state.js';

let activeId  = null;
let searchQuery = '';

// ── Public API ─────────────────────────────────────────────────────────────

export function initSidebar() {
  // Search
  document.getElementById('sb-search')?.addEventListener('input', e => {
    searchQuery = e.target.value.toLowerCase().trim();
    renderSidebar();
  });

  // Mobile open / close
  document.getElementById('sb-hamburger')?.addEventListener('click', openSidebar);
  document.getElementById('sb-close')?.addEventListener('click', closeSidebar);
  document.getElementById('sb-overlay')?.addEventListener('click', closeSidebar);

  // Group collapse + item click (delegated to sb-groups)
  document.getElementById('sb-groups')?.addEventListener('click', e => {
    const hdr = e.target.closest('.sb-group-hdr');
    if (hdr) {
      hdr.closest('.sb-group').classList.toggle('collapsed');
      return;
    }
    const item = e.target.closest('.sb-item');
    if (item) {
      const id = Number(item.dataset.id);
      // open-flashcard is handled by main.js to switch view + go to card
      document.dispatchEvent(new CustomEvent('open-flashcard', { detail: { id } }));
      closeSidebar();
    }
  });

  // Keep active highlight in sync with card navigation
  document.addEventListener('card-changed', e => {
    activeId = e.detail.id;
    // Lightweight update — toggle class on existing items, no full re-render
    document.querySelectorAll('.sb-item').forEach(el => {
      el.classList.toggle('active', Number(el.dataset.id) === activeId);
    });
    // Scroll active item into view within the sidebar
    const activeEl = document.querySelector(`.sb-item[data-id="${activeId}"]`);
    activeEl?.scrollIntoView({ block: 'nearest', behavior: 'smooth' });
  });
}

export function renderSidebar() {
  const problems = window.__problems || [];
  const groups = { unknown: [], familiar: [], known: [] };

  problems.forEach(p => {
    if (searchQuery) {
      const q = searchQuery;
      const hit = p.name.toLowerCase().includes(q)
                || p.pattern.toLowerCase().includes(q)
                || String(p.id).includes(q);
      if (!hit) return;
    }
    groups[getStage(p.id)].push(p);
  });

  const container = document.getElementById('sb-groups');
  if (!container) return;

  const stageOrder = [
    { key: 'unknown',  label: 'Unknown',  color: 'red'   },
    { key: 'familiar', label: 'Familiar', color: 'amber' },
    { key: 'known',    label: 'Known',    color: 'green' },
  ];

  container.innerHTML = stageOrder.map(({ key, label, color }) => {
    const items = groups[key];
    if (!items.length && searchQuery) return '';  // hide empty groups during search
    const chevron = `<svg class="sb-chevron" width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><polyline points="6 9 12 15 18 9"/></svg>`;
    return `
      <div class="sb-group" data-stage="${key}">
        <div class="sb-group-hdr sb-${color}">
          <span class="sb-group-dot sb-dot-${color}"></span>
          <span class="sb-group-label">${label}</span>
          <span class="sb-group-count">${items.length}</span>
          ${chevron}
        </div>
        <div class="sb-group-items">
          ${items.map(p => `
            <div class="sb-item${p.id === activeId ? ' active' : ''}" data-id="${p.id}">
              <span class="sb-dot sb-dot-${color}"></span>
              <span class="sb-num">#${p.id}</span>
              <span class="sb-name">${p.name}</span>
            </div>
          `).join('')}
        </div>
      </div>`;
  }).join('');
}

// ── Helpers ────────────────────────────────────────────────────────────────

function openSidebar() {
  document.getElementById('sidebar')?.classList.add('open');
  document.getElementById('sb-overlay')?.classList.add('show');
  document.body.classList.add('sb-noscroll');
}

function closeSidebar() {
  document.getElementById('sidebar')?.classList.remove('open');
  document.getElementById('sb-overlay')?.classList.remove('show');
  document.body.classList.remove('sb-noscroll');
}
