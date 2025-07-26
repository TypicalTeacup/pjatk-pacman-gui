{
  description = "GUI project dev shell";

  inputs.nixpkgs.url = "github:NixOS/nixpkgs/nixos-24.05";

  outputs = {
    self,
    nixpkgs,
  }: let
    system = "x86_64-linux"; # change to "aarch64-darwin" for apple sillicon macs
    pkgs = import nixpkgs {inherit system;};
  in {
    devShells.${system}.default = pkgs.mkShell {
      name = "gui-pro-shell";

      buildInputs = with pkgs; [
        jdk
      ];

      nativeBuildInputs = with pkgs; [
        gradle
      ];
    };
  };
}
