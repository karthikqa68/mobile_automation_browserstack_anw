pipeline {
    agent any
    triggers {
        githubPush()
    }
    stages {
        stage('Test') {
            agent {
                label 'macos-latest'
            }
            environment {
                ANDROID_HOME = "${WORKSPACE}/android-sdk"
                ANDROID_SDK_ROOT = "${WORKSPACE}/android-sdk"
                ANDROID_AVD_HOME = "${WORKSPACE}/.config/.android/avd"
                JAVA_HOME = '/Library/Java/JavaVirtualMachines/adoptopenjdk-17.jdk/Contents/Home'
                JAVA_HOME_11_X64 = '/Library/Java/JavaVirtualMachines/adoptopenjdk-17.jdk/Contents/Home'
            }
            steps {
                script {
                    checkout scm
                    echo 'Building...'
                }
                script {
                    sh 'echo $ANDROID_SDK_ROOT'
                }
                script {
                    dir("${ANDROID_SDK_ROOT}") {
                        sh 'sdkmanager --install "build-tools;29.0.0" "platform-tools" "platforms;android-29" "cmdline-tools;latest" "emulator"'
                    }
                }
                script {
                    sh '''
                        echo $ANDROID_HOME
                        echo $ANDROID_SDK_ROOT
                        ls -la $ANDROID_SDK_ROOT
                        ls -la $ANDROID_SDK_ROOT/cmdline-tools
                    '''
                }
                script {
                    sh '''
                        echo "$ANDROID_SDK_ROOT/cmdline-tools/latest/bin" >> $GITHUB_PATH
                        echo "$ANDROID_SDK_ROOT/platform-tools" >> $GITHUB_PATH
                        echo "$ANDROID_SDK_ROOT/emulator" >> $GITHUB_PATH
                        echo "$ANDROID_SDK_ROOT/platforms" >> $GITHUB_PATH
                        echo "$ANDROID_SDK_ROOT/tools" >> $GITHUB_PATH
                        echo "y" | $ANDROID_SDK_ROOT/cmdline-tools/latest/bin/sdkmanager --sdk_root=$ANDROID_SDK_ROOT "platform-tools" "platforms;android-29" "emulator"
                    '''
                }
                script {
                    sh 'echo "y" | $ANDROID_SDK_ROOT/cmdline-tools/latest/bin/sdkmanager --sdk_root=$ANDROID_SDK_ROOT "system-images;android-29;google_apis;x86_64"'
                }
                script {
                    sh '$ANDROID_SDK_ROOT/cmdline-tools/latest/bin/sdkmanager --update --sdk_root=$ANDROID_SDK_ROOT'
                }
                script {
                    sh '''
                        ls -la $ANDROID_SDK_ROOT
                        ls -la $ANDROID_SDK_ROOT/system-images/android-29
                    '''
                }
                script {
                    sh 'adb start-server'
                }
                script {
                    dir("${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin") {
                        sh 'echo "no" | ./avdmanager create avd --force --name emulator-5554 --package "system-images;android-29;google_apis;x86_64" --device "pixel" --sdcard 512M'
                    }
                }
                script {
                    sh 'echo "no" | $ANDROID_SDK_ROOT/cmdline-tools/latest/bin/avdmanager create avd --force --name emulator-5554 --package "system-images;android-29;google_apis;x86_64" --device "pixel" --sdcard 512M'
                }
                script {
                    sh 'avdmanager list avd'
                }
                script {
                    sh '$ANDROID_SDK_ROOT/emulator/emulator -list-avds'
                }
                script {
                    sh '''
                        cd android
                        mvn install -DskipTests
                    '''
                }
                script {
                    sh '''
                        cd android
                        mvn clean install
                    '''
                }
                script {
                    sh '''
                        $ANDROID_SDK_ROOT/emulator/emulator -avd emulator-5554 -no-snapshot -no-boot-anim -no-window -http-proxy localhost -no-audio -logcat *:e -memory 2048 -wipe-data -verbose &
                        sleep 500
                    '''
                }
                script {
                    sh 'adb devices'
                }
                script {
                    sh 'adb shell echo "Connection successful"'
                }
                script {
                    sh 'adb logcat | grep -iE "error|warning" >> emulator_logs.txt &'
                }
                script {
                    sh 'npm install -g appium@v1.22'
                }
                script {
                    sh 'npm install -g appium-doctor'
                }
                script {
                    sh 'appium-doctor --android'
                }
                script {
                    sh 'appium -a 127.0.0.1 -p 4723 &'
                }
                script {
                    sleep 10
                }
                script {
                    sh '''
                        pwd
                        cd android
                        mvn clean test -Dsurefire.suiteXmlFiles=regression.xml -f pom.xml -X
                    '''
                }
            }
            post {
                always {
                    archiveArtifacts artifacts: 'android/reports', allowEmptyArchive: true
                }
                failure {
                    archiveArtifacts artifacts: 'android/reports', allowEmptyArchive: true
                }
            }
        }
    }
}
