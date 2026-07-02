const STATE_KEY = 'dsa-drill-tracker-v1';
const STAGES    = ['unknown', 'familiar', 'known'];

let progress = {};
let _onChangeCallback = null;

export function onProgressChange(fn) {
  _onChangeCallback = fn;
}

function notify() {
  if (_onChangeCallback) _onChangeCallback();
}

// ── Accessors ──────────────────────────────────────────────────────────────

export function getStage(id)    { return (progress[id] || {}).stage    || 'unknown'; }
export function getReviewed(id) { return (progress[id] || {}).reviewed || false; }
export const STAGES_LIST = STAGES;

// ── Mutations ──────────────────────────────────────────────────────────────

export function advance(id) {
  const i = STAGES.indexOf(getStage(id));
  if (i < STAGES.length - 1) {
    setProgress(id, { stage: STAGES[i + 1], reviewed: true });
    saveState();
    notify();
  }
}

export function toggleReview(id) {
  setProgress(id, { reviewed: !getReviewed(id) });
  saveState();
  notify();
}

export function resetProgress() {
  progress = {};
  saveState();
  notify();
}

function setProgress(id, patch) {
  if (!progress[id]) progress[id] = { stage: 'unknown', reviewed: false };
  Object.assign(progress[id], patch);
}

// ── Persistence ────────────────────────────────────────────────────────────

function persistLocal() {
  try { localStorage.setItem(STATE_KEY, JSON.stringify(progress)); } catch(e) {}
}

export async function saveState() {
  setSyncStatus('syncing');
  persistLocal();

  const problems = window.__problems || [];
  const payload = problems.map(p => ({
    id: p.id,
    stage: getStage(p.id),
    reviewed: getReviewed(p.id),
  }));

  try {
    await fetch('/api/state', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    });
    setSyncStatus('synced');
  } catch(e) {
    setSyncStatus('');
  }
}

export async function loadState() {
  try {
    const cached = localStorage.getItem(STATE_KEY);
    if (cached) {
      const parsed = JSON.parse(cached);
      if (Array.isArray(parsed)) {
        parsed.forEach(p => { progress[p.id] = { stage: p.stage, reviewed: p.reviewed }; });
      } else {
        progress = parsed;
      }
    }
  } catch(e) {}

  try {
    const res = await fetch('/api/state');
    if (res.ok) {
      const saved = await res.json();
      if (Array.isArray(saved)) {
        progress = {};
        saved.forEach(p => { progress[p.id] = { stage: p.stage, reviewed: p.reviewed }; });
        persistLocal();
      }
    }
  } catch(e) {
    console.warn('KV unavailable, using local cache', e);
  }

  setSyncStatus('synced');
}

// ── Sync status UI ─────────────────────────────────────────────────────────

function setSyncStatus(state) {
  const el = document.getElementById('sync-status');
  if (!el) return;
  if (state === 'syncing') { el.textContent = '● syncing…'; el.className = 'sync-status syncing'; }
  else if (state === 'synced') { el.textContent = '✓ synced'; el.className = 'sync-status synced'; }
  else { el.textContent = ''; el.className = 'sync-status'; }
}
