const CACHE = 'dsa-drills-v3';

const PRECACHE = [
  '/',
  '/styles/base.css',
  '/styles/nav.css',
  '/styles/tracker.css',
  '/styles/flashcards.css',
  '/js/main.js',
  '/js/state.js',
  '/js/tracker.js',
  '/js/flashcards.js',
  '/problems.json',
  '/exercise-filenames.json',
  '/manifest.json',
];

self.addEventListener('install', e => {
  e.waitUntil(
    caches.open(CACHE).then(c => c.addAll(PRECACHE))
  );
  self.skipWaiting();
});

self.addEventListener('activate', e => {
  e.waitUntil(
    caches.keys().then(keys =>
      Promise.all(keys.filter(k => k !== CACHE).map(k => caches.delete(k)))
    )
  );
  self.clients.claim();
});

self.addEventListener('fetch', e => {
  const url = new URL(e.request.url);

  // API: network only, never cache
  if (url.pathname.startsWith('/api/')) {
    e.respondWith(
      fetch(e.request).catch(() => new Response('null', { headers: { 'Content-Type': 'application/json' } }))
    );
    return;
  }

  // JS + CSS: network-first — always fresh, cache only as offline fallback
  if (url.pathname.match(/\.(js|css)$/)) {
    e.respondWith(
      fetch(e.request)
        .then(res => {
          caches.open(CACHE).then(c => c.put(e.request, res.clone()));
          return res;
        })
        .catch(() => caches.match(e.request))
    );
    return;
  }

  // HTML navigation: network-first
  if (e.request.mode === 'navigate') {
    e.respondWith(
      fetch(e.request)
        .then(res => {
          caches.open(CACHE).then(c => c.put(e.request, res.clone()));
          return res;
        })
        .catch(() => caches.match('/'))
    );
    return;
  }

  // Everything else (JSON, images): cache-first
  e.respondWith(
    caches.match(e.request).then(cached => cached || fetch(e.request))
  );
});
