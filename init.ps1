$workspace = "C:\Users\chenv\OneDrive\文件\programs\java\AlgorithmsPart1"

function build {
    param (
        $target = $nil
    )
    if (!$target) {
        $targets = Get-ChildItem *.java | %{[io.path]::GetFileNameWithoutExtension($_.Name)};
        $target = $($targets | fzf);
    }
    if (!$target) {return;}

    javac -cp "$workspace\algs4.jar" "$target.java";
}
function run {
    param ( $target = $nil, $infile = $nil)

    if (!$target) {
        $targets = Get-ChildItem *.java | %{[io.path]::GetFileNameWithoutExtension($_.Name)};
        $target = $($targets | fzf);
    }
    if (!$target) {return;}

    if ($infile) {
        Get-Content $infile | java -cp "$workspace\algs4.jar;." $target;
    } else {
        java -cp "$workspace\algs4.jar;." $target;
    }
}
