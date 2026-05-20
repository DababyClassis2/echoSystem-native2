{ pkgs, ... }: {
  channel = "stable-24.11";

  packages = [
    pkgs.jdk17
    pkgs.gradle

    # Android SDK + tools
    pkgs.android-tools
    pkgs.androidsdk
    pkgs.androidsdk.platform-tools
    pkgs.androidsdk.build-tools-34-0-0
    pkgs.androidsdk.cmdline-tools-latest
  ];

  env = {
    ANDROID_HOME = "${pkgs.androidsdk}/libexec/android-sdk";
    ANDROID_SDK_ROOT = "${pkgs.androidsdk}/libexec/android-sdk";
    JAVA_HOME = "${pkgs.jdk17}";
  };

  idx = {
    extensions = [
      "google.gemini-cli-vscode-ide-companion"
    ];

    previews = {
      enable = true;
      previews = { };
    };

    workspace = {
      onCreate = {
        default.openFiles = [ ".idx/dev.nix" "README.md" ];
      };
      onStart = { };
    };
  };
}
