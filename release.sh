#!/bin/bash
gradlew assembleRelease
## 正式版要打包成 so 的资源的时候使用
#cat app/build/outputs/apk/release/app-release.apk > ../loadSandHook/dex/res.zip
#sh ../loadSandHook/dex/buildres.sh


## debug 测试的时候用
adb push app/build/outputs/apk/release/app-release.apk /data/local/tmp





