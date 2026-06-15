# Dev Container Setup

This dev container provides an isolated Java development environment for DSA practice.

## What's Included

- **Ubuntu-based container** with common development utilities
- **SDKMAN** for Java version management
- **Java 21** (OpenJDK) as default
- **GitHub CLI** for repository operations
- **Git** with configuration from host
- **VS Code Java extensions** (Language Server, Debugger, Test Runner)

## First Time Setup

1. Open this repository in VS Code
2. Install the "Dev Containers" extension
3. When prompted, click "Reopen in Container" (or use Command Palette: `Dev Containers: Reopen in Container`)
4. Wait for initial setup (~3-5 minutes)
5. Once inside container, authenticate GitHub CLI:
   ```bash
   gh auth login
   ```

## Using SDKMAN

SDKMAN is pre-installed and configured in your shell.

```bash
# Check current Java version
java -version

# List all available Java versions
sdk list java

# Install a specific Java version
sdk install java 17.0.10-open

# Switch to a different Java version (session only)
sdk use java 17.0.10-open

# Set a new default Java version
sdk default java 21.0.2-open

# Install other JVM tools
sdk install gradle
sdk install maven
```

## Troubleshooting

**Container won't build:**
- Check Docker is running
- Check internet connection (needs to download base image)
- Try: Command Palette → "Dev Containers: Rebuild Container"

**SDKMAN not found:**
- Restart your terminal or run: `source ~/.sdkman/bin/sdkman-init.sh`

**Java not found:**
- Verify installation: `sdk current java`
- Reinstall if needed: `sdk install java 21.0.2-open`

**GitHub CLI authentication:**
- Run: `gh auth login`
- Follow interactive prompts
- Choose HTTPS or SSH based on your preference
