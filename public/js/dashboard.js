import { getStage, getReviewed } from './state.js';
import { openDeck } from './flashcards.js';

// ── Public API ─────────────────────────────────────────────────────────────

export function initDashboard() {
  // Jump-in buttons
  document.querySelectorAll('.dash-jbtn').forEach(btn => {
    btn.addEventListener('click', () => openDeck(btn.dataset.deck));
  });

  // Reset button
  document.getElementById('reset-btn')?.addEventListener('click', async () => {
    if (!confirm('Reset all progress? This clears every stage and review mark.')) return;
    const { resetProgress } = await import('./state.js');
    resetProgress();
  });
}

export function renderDashboard() {
  const problems = window.__problems || [];
  if (!problems.length) return;

  // ── Counts ────────────────────────────────────────────────────────────────
  const counts = { unknown: 0, familiar: 0, known: 0, reviewed: 0 };
  problems.forEach(p => {
    counts[getStage(p.id)]++;
    if (getReviewed(p.id)) counts.reviewed++;
  });
  const total = problems.length;

  // ── Stat cards ────────────────────────────────────────────────────────────
  setText('c-total',    total);
  setText('c-known',    counts.known);
  setText('c-familiar', counts.familiar);
  setText('c-reviewed', counts.reviewed);

  const knownPct    = pct(counts.known,    total);
  const familiarPct = pct(counts.familiar, total);
  const reviewedPct = pct(counts.reviewed, total);

  setText('c-known-pct',    `${knownPct}% of deck`);
  setText('c-reviewed-pct', `${reviewedPct}% reviewed`);

  setWidth('c-known-bar',    knownPct);
  setWidth('c-familiar-bar', familiarPct);
  setWidth('c-reviewed-bar', reviewedPct);

  // ── Ring legend labels ────────────────────────────────────────────────────
  setText('leg-known',    counts.known);
  setText('leg-familiar', counts.familiar);
  setText('leg-unknown',  counts.unknown);

  // ── Jump-in counts ────────────────────────────────────────────────────────
  setText('jbtn-familiar',  counts.familiar);
  setText('jbtn-unknown',   counts.unknown);
  setText('jbtn-unreviewed', total - counts.reviewed);
  setText('jbtn-all',       total);

  // ── Ring ──────────────────────────────────────────────────────────────────
  renderRing(counts, total);

  // ── Patterns ──────────────────────────────────────────────────────────────
  renderPatterns(problems);

  // ── Difficulty ────────────────────────────────────────────────────────────
  renderDifficulty(problems);

  // ── Focus ──────────────────────────────────────────────────────────────────
  renderFocus(problems);
}

// ── Ring ───────────────────────────────────────────────────────────────────

function renderRing(counts, total) {
  const el = document.getElementById('dash-ring');
  if (!el) return;

  const R  = 54;
  const cx = 70, cy = 70;
  const sw = 10;
  const C  = 2 * Math.PI * R;

  const kPct = total ? counts.known    / total : 0;
  const fPct = total ? counts.familiar / total : 0;
  const uPct = total ? counts.unknown  / total : 0;

  function arc(fraction, offsetFraction, color) {
    const len = C * fraction;
    if (len <= 0) return '';
    return `<circle cx="${cx}" cy="${cy}" r="${R}" fill="none"
      stroke="${color}" stroke-width="${sw}"
      stroke-dasharray="${len} ${C - len}"
      stroke-dashoffset="${-(offsetFraction * C)}"
      transform="rotate(-90 ${cx} ${cy})"/>`;
  }

  const donePct = total ? Math.round((counts.known + counts.familiar) / total * 100) : 0;
  el.innerHTML = `<svg viewBox="0 0 140 140" width="130" height="130">
    <circle cx="${cx}" cy="${cy}" r="${R}" fill="none" stroke="var(--panel-2)" stroke-width="${sw}"/>
    ${arc(kPct,             0,           'var(--green)')}
    ${arc(fPct,             kPct,        'var(--amber)')}
    ${arc(uPct,             kPct + fPct, 'var(--red)')}
    <text x="${cx}" y="${cy - 5}" text-anchor="middle"
      fill="var(--text)" font-size="22" font-weight="700" font-family="var(--mono)">${donePct}%</text>
    <text x="${cx}" y="${cy + 14}" text-anchor="middle"
      fill="var(--text-faint)" font-size="10" font-family="var(--sans)" letter-spacing=".06em">DONE</text>
  </svg>`;
}

// ── Patterns ───────────────────────────────────────────────────────────────

function renderPatterns(problems) {
  const el = document.getElementById('dash-patterns');
  if (!el) return;

  const map = {};
  problems.forEach(p => {
    if (!map[p.pattern]) map[p.pattern] = { total: 0, known: 0, familiar: 0 };
    map[p.pattern].total++;
    const s = getStage(p.id);
    if (s === 'known')    map[p.pattern].known++;
    if (s === 'familiar') map[p.pattern].familiar++;
  });

  // Sort by progress ascending (least mastered first)
  const sorted = Object.entries(map).sort((a, b) => {
    const sa = (a[1].known + a[1].familiar * 0.5) / a[1].total;
    const sb = (b[1].known + b[1].familiar * 0.5) / b[1].total;
    return sa - sb;
  });

  el.innerHTML = sorted.map(([pat, d]) => `
    <div class="dash-pat-row">
      <div class="dash-pat-name" title="${pat}">${pat}</div>
      <div class="dash-pat-bar-wrap">
        <div class="dash-pat-bar">
          <div style="width:${pct(d.known,    d.total)}%; background:var(--green); height:100%"></div>
          <div style="width:${pct(d.familiar, d.total)}%; background:var(--amber); height:100%"></div>
        </div>
        <span class="dash-pat-nums">${d.known + d.familiar}/${d.total}</span>
      </div>
    </div>`).join('');
}

// ── Difficulty ─────────────────────────────────────────────────────────────

function renderDifficulty(problems) {
  const el = document.getElementById('dash-difficulty');
  if (!el) return;

  const diffs = ['Easy', 'Medium', 'Hard'];
  const map   = {};
  diffs.forEach(d => { map[d] = { total: 0, known: 0, familiar: 0, unknown: 0 }; });

  problems.forEach(p => {
    if (!map[p.diff]) return;
    map[p.diff].total++;
    map[p.diff][getStage(p.id)]++;
  });

  el.innerHTML = diffs.map(d => {
    const data  = map[d];
    if (!data.total) return '';
    const color = d === 'Hard' ? 'var(--red)' : d === 'Medium' ? 'var(--amber)' : 'var(--green)';
    return `
      <div class="dash-diff-row">
        <span class="dash-diff-label" style="color:${color}">${d}</span>
        <div class="dash-pat-bar">
          <div style="width:${pct(data.known,    data.total)}%; background:var(--green); height:100%"></div>
          <div style="width:${pct(data.familiar, data.total)}%; background:var(--amber); height:100%"></div>
          <div style="width:${pct(data.unknown,  data.total)}%; background:var(--red-bg); height:100%"></div>
        </div>
        <span class="dash-pat-nums">${data.known + data.familiar}/${data.total}</span>
      </div>`;
  }).join('');
}

// ── Focus ──────────────────────────────────────────────────────────────────

function renderFocus(problems) {
  const el = document.getElementById('dash-focus');
  if (!el) return;

  const items = [];

  // Group by pattern — find untouched or least-started patterns
  const patMap = {};
  problems.forEach(p => {
    if (!patMap[p.pattern]) patMap[p.pattern] = { total: 0, done: 0 };
    patMap[p.pattern].total++;
    const s = getStage(p.id);
    if (s === 'known' || s === 'familiar') patMap[p.pattern].done++;
  });

  const untouched = Object.entries(patMap)
    .filter(([, d]) => d.done === 0)
    .sort((a, b) => b[1].total - a[1].total)
    .slice(0, 2);

  untouched.forEach(([pat, d]) => {
    items.push({ color: 'var(--amber)', text: `${pat} — 0 of ${d.total} started` });
  });

  // Add difficulty hint if Medium or Hard has many unknowns remaining
  const diffMap = { Easy: 0, Medium: 0, Hard: 0 };
  problems.forEach(p => {
    if (getStage(p.id) === 'unknown' && diffMap[p.diff] !== undefined) diffMap[p.diff]++;
  });
  const worstDiff = ['Hard', 'Medium', 'Easy'].find(d => diffMap[d] > 0);
  if (worstDiff && items.length < 3) {
    items.push({ color: 'var(--red)', text: `${worstDiff} difficulty — ${diffMap[worstDiff]} unknown remaining` });
  }

  if (!items.length) {
    el.innerHTML = `<p class="dash-focus-done">🎉 All patterns started!</p>`;
    return;
  }

  el.innerHTML = `<div class="dash-focus-list">
    ${items.map(item => `
      <div class="dash-focus-item">
        <span class="dash-focus-dot" style="background:${item.color}"></span>
        <span>${item.text}</span>
      </div>`).join('')}
  </div>`;
}

// ── Utilities ──────────────────────────────────────────────────────────────

function pct(n, total) {
  return total ? Math.round(n / total * 100) : 0;
}

function seg(width, color) {
  return `<div class="progress-seg" style="width:${width}%; background:${color}"></div>`;
}

function setText(id, val) {
  const el = document.getElementById(id);
  if (el) el.textContent = val;
}

function setWidth(id, widthPct) {
  const el = document.getElementById(id);
  if (el) el.style.width = widthPct + '%';
}
