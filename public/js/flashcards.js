import { getStage, getReviewed, advance, toggleReview } from './state.js';

const GH_BASE = 'https://github.com/akojimsg/c1-codesignal-drills/blob/main/exercises/';

let deck      = [];
let fcIdx     = 0;
let fcFilter  = 'all';

// ── Filter counts ──────────────────────────────────────────────────────────

function filterCounts() {
  const problems = window.__problems || [];
  const c = { unknown: 0, familiar: 0, known: 0, reviewed: 0, unreviewed: 0 };
  problems.forEach(p => {
    c[getStage(p.id)]++;
    if (getReviewed(p.id)) c.reviewed++; else c.unreviewed++;
  });
  return c;
}

function updateFilterCounts() {
  const problems = window.__problems || [];
  const c = filterCounts();
  document.querySelectorAll('#fc-filters .filter-btn').forEach(b => {
    const f = b.dataset.fc;
    if      (f === 'all')       b.textContent = `All (${problems.length})`;
    else if (f === 'reviewed')  b.textContent = `Reviewed (${c.reviewed})`;
    else if (f === 'unreviewed') b.textContent = `Unreviewed (${c.unreviewed})`;
    else b.textContent = `${f.charAt(0).toUpperCase() + f.slice(1)} (${c[f] || 0})`;
  });
}

// ── Deck ───────────────────────────────────────────────────────────────────

export function buildDeck() {
  const problems = window.__problems || [];
  updateFilterCounts();

  if      (fcFilter === 'all')       deck = [...problems];
  else if (fcFilter === 'reviewed')  deck = problems.filter(p => getReviewed(p.id));
  else if (fcFilter === 'unreviewed') deck = problems.filter(p => !getReviewed(p.id));
  else deck = problems.filter(p => getStage(p.id) === fcFilter);

  fcIdx = Math.min(fcIdx, Math.max(0, deck.length - 1));
  document.getElementById('card-3d').classList.remove('flipped');
  renderCard();
}

// ── Card render ────────────────────────────────────────────────────────────

function ghUrl(id) {
  const filenames = window.__filenames || {};
  return filenames[id] ? GH_BASE + filenames[id] : '#';
}

export function renderCard() {
  if (!deck.length) {
    document.getElementById('fc-title').textContent = 'No cards match';
    document.getElementById('fc-counter').textContent = '0 / 0';
    return;
  }

  const p        = deck[fcIdx];
  const stage    = getStage(p.id);
  const reviewed = getReviewed(p.id);
  const gh       = ghUrl(p.id);

  const numLink = document.getElementById('fc-num-link');
  numLink.textContent = `#${p.id}`;
  numLink.href = gh;

  document.getElementById('fc-title').textContent = p.name;
  document.getElementById('fc-meta').innerHTML =
    `<span class="pill pill-${p.diff}">${p.diff}</span>` +
    `<span class="pill pill-pat">${p.pattern}</span>`;

  document.getElementById('fc-btitle').textContent = p.name;
  document.getElementById('fc-bnum').textContent = `#${p.id} · ${p.diff} · ${p.pattern}`;
  document.getElementById('fc-prob').textContent = p.problem;
  document.getElementById('fc-ex').textContent   = p.example;
  document.getElementById('fc-insight').textContent = p.insight;
  document.getElementById('fc-code').innerHTML   = p.code;
  document.getElementById('fc-lc-link').href     = `https://leetcode.com/problems/${p.slug}/`;
  document.getElementById('fc-gh-link').href     = gh;
  document.getElementById('fc-counter').textContent = `${fcIdx + 1} / ${deck.length}`;

  updateStrip(p.id, stage, reviewed);
  document.getElementById('card-3d').classList.remove('flipped');
}

// ── Tracking strip ─────────────────────────────────────────────────────────

function updateStrip(id, stage, reviewed) {
  const badge = document.getElementById('strip-badge');
  badge.className = 'badge badge-' + stage;
  badge.textContent = stage;

  const advBtn = document.getElementById('strip-advance');
  if (stage === 'unknown') {
    advBtn.textContent = 'Unknown → Familiar';
    advBtn.className = 'strip-btn advance';
    advBtn.disabled = false;
  } else if (stage === 'familiar') {
    advBtn.textContent = 'Familiar → Known';
    advBtn.className = 'strip-btn advance';
    advBtn.disabled = false;
  } else {
    advBtn.textContent = '✓ Known';
    advBtn.className = 'strip-btn at-known';
    advBtn.disabled = true;
  }

  const revBtn = document.getElementById('strip-review');
  if (reviewed) {
    revBtn.className = 'strip-btn reviewed';
    revBtn.innerHTML = `<svg width="11" height="11" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><polyline points="20 6 9 17 4 12"/></svg> Reviewed`;
  } else {
    revBtn.className = 'strip-btn';
    revBtn.textContent = 'Mark reviewed';
  }
}

// ── Navigation ─────────────────────────────────────────────────────────────

let animating = false;

export function flipCard() {
  if (animating) return;
  document.getElementById('card-3d').classList.toggle('flipped');
}

function navigate(direction) {
  if (animating || !deck.length) return;
  animating = true;

  const card    = document.getElementById('card-3d');
  const outAnim = direction === 'next' ? 'animate__fadeOutLeft'  : 'animate__fadeOutRight';
  const inAnim  = direction === 'next' ? 'animate__fadeInRight'  : 'animate__fadeInLeft';

  card.classList.add('animate__animated', outAnim);

  card.addEventListener('animationend', () => {
    card.classList.remove('animate__animated', outAnim);
    card.style.visibility = 'hidden';

    fcIdx = direction === 'next'
      ? (fcIdx + 1) % deck.length
      : (fcIdx - 1 + deck.length) % deck.length;

    card.style.transition = 'none';
    card.classList.remove('flipped');
    setTimeout(() => { card.style.transition = ''; }, 20);

    renderCard();

    card.style.visibility = 'visible';
    card.classList.add('animate__animated', inAnim);

    card.addEventListener('animationend', () => {
      card.classList.remove('animate__animated', inAnim);
      animating = false;
    }, { once: true });

  }, { once: true });
}

export function nextCard() { navigate('next'); }
export function prevCard() { navigate('prev'); }

export function goToCard(problemId) {
  // Reset filter to 'all' so the problem is guaranteed in the deck
  fcFilter = 'all';
  document.querySelectorAll('#fc-filters .filter-btn').forEach(b => {
    b.classList.toggle('active', b.dataset.fc === 'all');
  });
  buildDeck();
  const idx = deck.findIndex(p => p.id === problemId);
  if (idx !== -1) {
    fcIdx = idx;
    document.getElementById('card-3d').classList.remove('flipped');
    renderCard();
  }
}

export function shuffleDeck() {
  for (let i = deck.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [deck[i], deck[j]] = [deck[j], deck[i]];
  }
  fcIdx = 0;
  document.getElementById('card-3d').classList.remove('flipped');
  renderCard();
}

// ── Init ───────────────────────────────────────────────────────────────────

export function initFlashcards() {
  document.getElementById('fc-filters').addEventListener('click', e => {
    const btn = e.target.closest('.filter-btn');
    if (!btn) return;
    fcFilter = btn.dataset.fc;
    document.querySelectorAll('#fc-filters .filter-btn').forEach(b => b.classList.remove('active'));
    btn.classList.add('active');
    fcIdx = 0;
    buildDeck();
  });

  document.getElementById('btn-prev').addEventListener('click', prevCard);
  document.getElementById('btn-next').addEventListener('click', nextCard);
  document.getElementById('btn-shuffle').addEventListener('click', shuffleDeck);

  document.getElementById('strip-advance').addEventListener('click', () => {
    if (deck.length) advance(deck[fcIdx].id);
  });

  document.getElementById('strip-review').addEventListener('click', () => {
    if (deck.length) toggleReview(deck[fcIdx].id);
  });

  // Native touch — swipe left/right to navigate, tap to flip
  const SWIPE_THRESHOLD = 50;
  let touchStartX  = 0;
  let touchStartY  = 0;
  let touchDelta   = 0;
  let touchAxis    = null;  // 'h' | 'v' | null (undecided)
  let touchHandled = false; // suppress ghost click after touchend

  const scene = document.getElementById('scene');

  scene.addEventListener('touchstart', e => {
    touchStartX  = e.changedTouches[0].clientX;
    touchStartY  = e.changedTouches[0].clientY;
    touchDelta   = 0;
    touchAxis    = null;
  }, { passive: true });

  scene.addEventListener('touchmove', e => {
    const dx = e.changedTouches[0].clientX - touchStartX;
    const dy = e.changedTouches[0].clientY - touchStartY;
    touchDelta = dx;

    // Lock scroll axis on first significant movement
    if (!touchAxis && (Math.abs(dx) > 8 || Math.abs(dy) > 8)) {
      touchAxis = Math.abs(dx) > Math.abs(dy) ? 'h' : 'v';
    }

    // Only block browser scroll when confirmed horizontal swipe
    if (touchAxis === 'h') e.preventDefault();
  }, { passive: false });

  scene.addEventListener('touchend', () => {
    if (touchAxis === 'h' && Math.abs(touchDelta) >= SWIPE_THRESHOLD) {
      if (touchDelta < 0) nextCard(); else prevCard();
    } else if (!touchAxis) {
      // Tap (no meaningful movement) — flip card
      flipCard();
      touchHandled = true;
      setTimeout(() => { touchHandled = false; }, 500);
    }
    touchStartX = 0;
  }, { passive: true });

  // Desktop click-to-flip (suppressed after touch to avoid ghost clicks)
  scene.addEventListener('click', e => {
    if (touchHandled) return;
    if (e.target.closest('a')) return; // let link clicks through
    flipCard();
  });
}
