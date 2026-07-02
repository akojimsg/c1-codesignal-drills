import { Redis } from '@upstash/redis';

const kv = new Redis({
  url: process.env.KV_REST_API_URL,
  token: process.env.KV_REST_API_TOKEN,
});

const STATE_KEY = 'dsa-drill-tracker-state';

export default async function handler(req, res) {
  if (req.method === 'GET') {
    const data = await kv.get(STATE_KEY);
    res.status(200).json(data ?? null);
    return;
  }

  if (req.method === 'POST') {
    const body = req.body;
    if (!body || !Array.isArray(body)) {
      res.status(400).json({ error: 'invalid payload' });
      return;
    }
    await kv.set(STATE_KEY, body);
    res.status(200).json({ ok: true });
    return;
  }

  res.status(405).end();
}
