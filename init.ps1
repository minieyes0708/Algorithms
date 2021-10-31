$basepath = "C:\Users\chenv\OneDrive\文件\programs\java\AlgorithmsPart1"
function make {
    $options = @(
        'build and run'
    )
    $option = $($options -join '`n' | fzf)

    switch ($option) {
        'build and run' {
            $target = $($args[0]);
            javac -cp "~\workspace\algs4.jar" "$target.java";
            java -cp "~\workspace\algs4.jar;." $target;
        }
    }
}
