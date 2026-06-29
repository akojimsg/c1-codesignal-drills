#!/bin/bash
set -e

echo "===================================="
echo "Setting up DSA Java Dev Environment"
echo "===================================="

# Install sdkman
echo "Installing SDKMAN..."
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"

# Install Java 21
echo "Installing Java 21 via SDKMAN..."
sdk install java 21.0.2-open
sdk default java 21.0.2-open

# Ensure nvm is sourced in shell config (installed via devcontainer feature)
NVM_INIT='export NVM_DIR="$HOME/.nvm" && [ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"'
if ! grep -q "nvm.sh" "$HOME/.bashrc" 2>/dev/null; then
  echo "$NVM_INIT" >> "$HOME/.bashrc"
fi
if ! grep -q "nvm.sh" "$HOME/.zshrc" 2>/dev/null; then
  echo "$NVM_INIT" >> "$HOME/.zshrc"
fi


# Git global config
echo "Configuring git..."
git config --global user.name "Ezekiel Akoji"
git config --global user.email "akojimsg@gmail.com"
git config --global init.defaultBranch main

# SSH setup for GitHub
echo "Configuring SSH for GitHub..."
mkdir -p "$HOME/.ssh"
chmod 700 "$HOME/.ssh"

if [ ! -f "$HOME/.ssh/id_ed25519_github" ]; then
  ssh-keygen -t ed25519 -C "akojimsg@gmail.com" -f "$HOME/.ssh/id_ed25519_github" -N ""
fi

cat > "$HOME/.ssh/config" << 'SSHEOF'
Host github.com
  HostName github.com
  User git
  IdentityFile ~/.ssh/id_ed25519_github
  IdentitiesOnly yes
SSHEOF
chmod 600 "$HOME/.ssh/config"

ssh-keyscan github.com >> "$HOME/.ssh/known_hosts" 2>/dev/null

echo ""
echo "Add this SSH public key to https://github.com/settings/ssh/new:"
echo ""
cat "$HOME/.ssh/id_ed25519_github.pub"
echo ""

# Verify installation
echo ""
echo "Installation complete!"
echo ""
echo "Java version:"
java -version
echo ""
echo "SDKMAN version:"
sdk version
echo ""
echo "Node version (nvm):"
export NVM_DIR="$HOME/.nvm" && [ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh" && node --version || echo "nvm not yet active — open a new shell"
echo ""
echo "To authenticate GitHub CLI, run:"
echo "  gh auth login --hostname github.com --git-protocol ssh --web"
echo ""
echo "Dev environment ready!"
