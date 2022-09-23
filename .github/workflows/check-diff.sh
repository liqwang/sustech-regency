#!/bin/bash
# 1.分割commits的Json字符串，获取commits数量
# https://www.yiibai.com/bash/bash-split-string.html(示例2：使用字符串拆分字符串)
# shellcheck disable=SC2016
echo "$COMMITS"
delimiter='"author": {' #设置分割字符串
s=$COMMITS$delimiter
splits=();
while [[ $s ]];
do
splits+=( "${s%%"$delimiter"*}" );
s=${s#*"$delimiter"};
done;
commitsNumber=$((${#splits[@]}-1))
echo "commitsNumber:$commitsNumber"

# 2.Diff HEAD with the commit number
diff=$(git diff --name-only HEAD~"$commitsNumber" --)
echo "$diff"

# 3.Check if a file under /frontend or /backend has changed (added, modified, deleted)
# https://www.cnblogs.com/drizzlewithwind/p/6281749.html(方法二)
# shellcheck disable=SC2076
if [[ $diff =~ 'frontend/' ]]
then
    frontendHasDiff='True'
else
    frontendHasDiff='False'
fi

# shellcheck disable=SC2076
if [[ $diff =~ 'backend/' ]]
then
    backendHasDiff='True'
else
    backendHasDiff='False'
fi

# 4.Set the output named "frontend_changed" and "backend_changed"
echo "::set-output name=frontend_changed::$frontendHasDiff"
echo "::set-output name=backend_changed::$backendHasDiff"
