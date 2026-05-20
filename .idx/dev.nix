{ pkgs, ... }: {
  channel = "stable-24.11";

  packages = [
    pkgs.jdk17
    pkgs.gradle
    pkgs.android-tools
  ];

  env = {
    JAVA_HOME = "${pkgs.jdk17}";
    ANDROID_HOME = "/workspace/.android-sdk";
    ANDROID_SDK_ROOT = "/workspace/.android-sdk";
  };

  idx = {
    extensions = [
      "google.gemini-cli-vscode-ide-companion"
    ];

    workspace = {
      onCreate = {
        default.openFiles = [ ".idx/dev.nix" "README.md" ];
      };
      onStart = { };
    };
  };
}
