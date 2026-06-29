import { readFileSync, writeFileSync, readdirSync } from 'fs';
import { join, dirname } from 'path';
import { fileURLToPath } from 'url';

const __dirname = dirname(fileURLToPath(import.meta.url));
const exercisesDir = join(__dirname, '../exercises');
const outputPath = join(__dirname, '../public/problems.json');

function escapeHtml(str) {
  return str.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
}

function extractField(text, field) {
  const re = new RegExp(`\\*\\s+${field}:\\s*(.+)`);
  const m = text.match(re);
  return m ? m[1].trim() : '';
}

function extractSlug(text) {
  const m = text.match(/https:\/\/leetcode\.com\/problems\/([^/]+)\//);
  return m ? m[1] : '';
}

function extractProblemStatement(text) {
  // Between end of header block (after constraints/difficulty/pattern lines) and Example 1
  const headerEnd = text.indexOf(' * Example');
  if (headerEnd === -1) return '';
  // Find start of problem description: after the Pattern line
  const patternIdx = text.indexOf(' * Pattern:');
  const afterPattern = text.indexOf('\n', patternIdx) + 1;
  // Skip blank comment lines
  const lines = text.slice(afterPattern, headerEnd).split('\n');
  const descLines = lines
    .map(l => l.replace(/^\s*\*\s?/, '').trimEnd())
    .filter(l => l.length > 0);
  return descLines.join(' ').trim();
}

function extractExample(text) {
  const m = text.match(/\* Example 1:\s*\n\s*\* Input:\s*(.+)\n\s*\* Output:\s*(.+)/);
  if (!m) return '';
  return `Input: ${m[1].trim()}\nOutput: ${m[2].trim()}`;
}

function extractInsight(text) {
  const start = text.indexOf('* INSIGHT:');
  if (start === -1) return '';
  const end = text.indexOf('*/', start);
  const block = text.slice(start + 10, end);
  return block
    .split('\n')
    .map(l => l.replace(/^\s*\*\s?/, '').trim())
    .filter(l => l.length > 0)
    .join(' ')
    .trim();
}

function extractCode(text) {
  // Find start: import statement or first class declaration after the comment blocks
  const importIdx = text.indexOf('\nimport ');
  const classIdx = text.search(/\nclass \w/);
  let start = -1;
  if (importIdx !== -1 && (classIdx === -1 || importIdx < classIdx)) {
    start = importIdx + 1;
  } else if (classIdx !== -1) {
    start = classIdx + 1;
  }
  if (start <= 0) return '';
  return escapeHtml(text.slice(start).trim());
}

function padId(id) {
  return String(id).padStart(2, '0');
}

const files = readdirSync(exercisesDir)
  .filter(f => f.endsWith('.java'))
  .sort();

const problems = [];

for (const file of files) {
  const text = readFileSync(join(exercisesDir, file), 'utf8');

  const idMatch = text.match(/\*\s+#(\d+)\s+\|/);
  if (!idMatch) continue;
  const id = parseInt(idMatch[1]);

  const nameMatch = text.match(/\*\s+#\d+\s+\|\s+(.+)/);
  const name = nameMatch ? nameMatch[1].trim() : '';

  const slug = extractSlug(text);
  const rawPattern = extractField(text, 'Pattern');
  const pattern = rawPattern.replace(/\s*\(.*\)$/, '').trim();
  const diff = extractField(text, 'Difficulty');
  const problem = extractProblemStatement(text);
  const example = extractExample(text);
  const insight = extractInsight(text);
  const code = extractCode(text);
  const filename = `${padId(id)}_${file.replace(/^\d+_/, '')}`;

  problems.push({ id, name, slug, pattern, diff, problem, example, insight, code, filename });
}

problems.sort((a, b) => a.id - b.id);

// Strip filename from output (internal use only)
const output = problems.map(({ filename, ...p }) => p);

writeFileSync(outputPath, JSON.stringify(output, null, 2));
console.log(`Wrote ${output.length} problems to ${outputPath}`);

// Also output filename map for use in HTML
const filenameMap = {};
problems.forEach(p => { filenameMap[p.id] = p.filename; });
writeFileSync(join(__dirname, '../public/exercise-filenames.json'), JSON.stringify(filenameMap, null, 2));
console.log('Wrote exercise-filenames.json');
