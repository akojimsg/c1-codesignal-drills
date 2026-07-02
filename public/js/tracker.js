import { getStage, getReviewed, advance, toggleReview } from './state.js';

let currentFilter = 'all';

export function renderTracker() {
  const problems = window.__problems || [];

  const c = { unknown: 0, familiar: 0, known: 0, reviewed: 0 };
  problems.forEach(p => {
    c[getStage(p.id)]++;
    if (getReviewed(p.id)) c.reviewed++;
  });

  ['unknown', 'familiar', 'known', 'reviewed'].forEach(k => {
    document.getElementById('c-' + k).textContent = c[k];
  });
  document.getElementById('c-total').textContent = problems.length;

  const n = problems.length;
  document.getElementById('progress-bar').innerHTML =
    `<div class="progress-seg" style="width:${c.unknown  / n * 100}%;background:var(--red)"></div>`  +
    `<div class="progress-seg" style="width:${c.familiar / n * 100}%;background:var(--amber)"></div>` +
    `<div class="progress-seg" style="width:${c.known    / n * 100}%;background:var(--green)"></div>`;

  const filtered = problems.filter(p => {
    if (currentFilter === 'all')       return true;
    if (currentFilter === 'Hard')      return p.diff === 'Hard';
    if (currentFilter === 'reviewed')  return getReviewed(p.id);
    if (currentFilter === 'unreviewed') return !getReviewed(p.id);
    return getStage(p.id) === currentFilter;
  });

  const empty = document.getElementById('empty-state');
  if (filtered.length === 0) {
    document.getElementById('tbody').innerHTML = '';
    empty.style.display = 'block';
  } else {
    empty.style.display = 'none';
    document.getElementById('tbody').innerHTML = filtered.map(p => {
      const stage    = getStage(p.id);
      const reviewed = getReviewed(p.id);
      return `
        <tr>
          <td class="id-cell">${p.id}</td>
          <td class="name-cell"><button class="name-btn" data-id="${p.id}">${p.name}</button></td>
          <td class="pattern-cell">${p.pattern}</td>
          <td class="diff-${p.diff}">${p.diff}</td>
          <td><span class="badge badge-${stage}">${stage}</span></td>
          <td><button class="review-toggle ${reviewed ? 'checked' : ''}" data-id="${p.id}" aria-label="Toggle reviewed"></button></td>
          <td>${stage !== 'known'
            ? `<button class="advance-btn" data-id="${p.id}">${stage === 'unknown' ? 'Familiar →' : 'Known →'}</button>`
            : `<span class="badge badge-known">Known</span>`}
          </td>
        </tr>`;
    }).join('');
  }
}

export function initTracker() {
  document.getElementById('t-filters').addEventListener('click', e => {
    const btn = e.target.closest('.filter-btn');
    if (!btn) return;
    currentFilter = btn.dataset.f;
    document.querySelectorAll('#t-filters .filter-btn').forEach(b => b.classList.remove('active'));
    btn.classList.add('active');
    renderTracker();
  });

  document.getElementById('tbody').addEventListener('click', e => {
    const nameBtn = e.target.closest('.name-btn');
    if (nameBtn) {
      document.dispatchEvent(new CustomEvent('open-flashcard', { detail: { id: Number(nameBtn.dataset.id) } }));
      return;
    }
    const advBtn = e.target.closest('.advance-btn');
    if (advBtn) { advance(Number(advBtn.dataset.id)); return; }
    const revBtn = e.target.closest('.review-toggle');
    if (revBtn) { toggleReview(Number(revBtn.dataset.id)); return; }
  });
}
