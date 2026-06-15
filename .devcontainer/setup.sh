#!/bin/bash
set -e

echo "===================================="
echo "Setting up DSA Java Dev Environment"
echo "===================================="

# Install sdkman
echo "📦 Installing SDKMAN..."
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"

# Install Java 21
echo "☕ Installing Java 21 via SDKMAN..."
sdk install java 21.0.2-open
sdk default java 21.0.2-open

# Verify installation
echo ""
echo "✅ Installation complete!"
echo ""
echo "Java version:"
java -version
echo ""
echo "SDKMAN version:"
sdk version

# Setup GitHub CLI authentication hint
echo ""
echo "📝 To authenticate GitHub CLI, run:"
echo "   gh auth login"
echo ""
echo "🚀 Dev environment ready!"
