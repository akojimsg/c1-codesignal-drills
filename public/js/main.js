import { loadState, onProgressChange } from './state.js';
import { initTracker, renderTracker } from './tracker.js';
import { initFlashcards, buildDeck, flipCard, nextCard, prevCard, goToCard } from './flashcards.js';

// ── Theme ──────────────────────────────────────────────────────────────────

const THEME_KEY = 'dsa-theme';

function applyTheme(theme) {
  document.documentElement.setAttribute('data-theme', theme);
  const btn = document.getElementById('theme-toggle');
  if (!btn) return;
  btn.innerHTML = theme === 'dark' ? sunIcon() : moonIcon();
  btn.title = theme === 'dark' ? 'Switch to light mode' : 'Switch to dark mode';
}

function initTheme() {
  const stored = localStorage.getItem(THEME_KEY);
  const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
  const theme = stored || (prefersDark ? 'dark' : 'light');
  applyTheme(theme);

  document.getElementById('theme-toggle').addEventListener('click', () => {
    const next = document.documentElement.getAttribute('data-theme') === 'dark' ? 'light' : 'dark';
    localStorage.setItem(THEME_KEY, next);
    applyTheme(next);
  });
}

function sunIcon() {
  return `<svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
    <circle cx="12" cy="12" r="5"/><line x1="12" y1="1" x2="12" y2="3"/><line x1="12" y1="21" x2="12" y2="23"/>
    <line x1="4.22" y1="4.22" x2="5.64" y2="5.64"/><line x1="18.36" y1="18.36" x2="19.78" y2="19.78"/>
    <line x1="1" y1="12" x2="3" y2="12"/><line x1="21" y1="12" x2="23" y2="12"/>
    <line x1="4.22" y1="19.78" x2="5.64" y2="18.36"/><line x1="18.36" y1="5.64" x2="19.78" y2="4.22"/>
  </svg>`;
}

function moonIcon() {
  return `<svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
    <path d="M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z"/>
  </svg>`;
}

// ── Nav ────────────────────────────────────────────────────────────────────

function initNav() {
  document.querySelectorAll('.nav-tab').forEach(btn => {
    btn.addEventListener('click', () => {
      const view = btn.dataset.view;
      document.querySelectorAll('.view').forEach(v => v.classList.remove('active'));
      document.querySelectorAll('.nav-tab').forEach(b => b.classList.remove('active'));
      document.getElementById('view-' + view).classList.add('active');
      btn.classList.add('active');
    });
  });
}

// ── Keyboard ───────────────────────────────────────────────────────────────

function initKeyboard() {
  document.addEventListener('keydown', e => {
    if (!document.getElementById('view-flashcards').classList.contains('active')) return;
    if (e.key === ' ' || e.key === 'Enter') { e.preventDefault(); flipCard(); }
    if (e.key === 'ArrowRight') nextCard();
    if (e.key === 'ArrowLeft')  prevCard();
  });
}

// ── Reset ──────────────────────────────────────────────────────────────────

function initReset() {
  document.getElementById('reset-btn').addEventListener('click', async () => {
    if (!confirm('Reset all progress? This clears every stage and review mark.')) return;
    const { resetProgress } = await import('./state.js');
    resetProgress();
  });
}

// ── Tracker → Flashcard jump ───────────────────────────────────────────────

function initOpenFlashcard() {
  document.addEventListener('open-flashcard', e => {
    document.querySelectorAll('.view').forEach(v => v.classList.remove('active'));
    document.querySelectorAll('.nav-tab').forEach(b => b.classList.remove('active'));
    document.getElementById('view-flashcards').classList.add('active');
    document.querySelector('.nav-tab[data-view="flashcards"]').classList.add('active');
    goToCard(e.detail.id);
  });
}

// ── Render all ─────────────────────────────────────────────────────────────

function renderAll() {
  renderTracker();
  buildDeck();
}

// ── Init ───────────────────────────────────────────────────────────────────

async function init() {
  const [problemsRes, filenamesRes] = await Promise.all([
    fetch('/problems.json'),
    fetch('/exercise-filenames.json'),
  ]);
  window.__problems  = await problemsRes.json();
  window.__filenames = await filenamesRes.json();

  initTheme();
  initNav();
  initKeyboard();
  initReset();
  initTracker();
  initFlashcards();
  initOpenFlashcard();

  onProgressChange(renderAll);

  await loadState();
  renderAll();
}

init();

if ('serviceWorker' in navigator) {
  navigator.serviceWorker.register('/sw.js').catch(console.error);
}
