# SimpleLedger (簡易記帳 App)

本專案為 Android 課程「簡易記帳 App」作業實作，採用 **Kotlin**、**XML Layout**、**AndroidX Fragment** 與 **ViewBinding** 架構開發。

---

## 🛠️ 開啟與執行方式
1. 解壓縮 `SimpleLedger.zip`。
2. 使用 **Android Studio** 開啟專案根目錄。
3. 等待 Gradle 完成 Sync 後，點擊右上角 **Run 'app'** 即可在模擬器或實體裝置上執行。

---

## 📋 環境版本說明
* **Android Studio**: 2024.x+ / 2026.x
* **Gradle Plugin (AGP)**: 8.x / 9.x
* **Kotlin Target / SDK**: Compile SDK 37, Min SDK 24 (Android 7.0)
* **核心依賴項目**:
  * `androidx.appcompat:appcompat`
  * `androidx.fragment:fragment-ktx`
  * `androidx.constraintlayout:constraintlayout`
  * `com.google.android.material:material`

---

## 🔍 作業要求對應實作位置

### 一、專案與架構設定
* 套件名稱與核心設定：[build.gradle.kts](file:///D:/android%20studio/MyApplication2/app/build.gradle.kts)
  * 啟用 ViewBinding：`buildFeatures { viewBinding = true }`
  * 引入 Fragment-KTX：`implementation(libs.androidx.fragment.ktx)`
* 啟動頁面宣告：[AndroidManifest.xml](file:///D:/android%20studio/MyApplication2/app/src/main/AndroidManifest.xml) （設定 `MainActivity` 為 `MAIN` / `LAUNCHER`）

### 二、Activity 畫面配置 (`activity_main.xml`)
* 檔案位置：[activity_main.xml](file:///D:/android%20studio/MyApplication2/app/src/main/res/layout/activity_main.xml)
* 實作說明：根元件為單一 `<androidx.fragment.app.FragmentContainerView>`，`id` 為 `@+id/container`，寬高均為 `match_parent`，未設定 `android:name` 或 `class`，避免重複初始化。

### 三、MainActivity 邏輯控制 (`MainActivity.kt`)
* 檔案位置：[MainActivity.kt](file:///D:/android%20studio/MyApplication2/app/src/main/java/com/example/myapplication/MainActivity.kt)
* 實作說明：採用 `AppCompatActivity`，僅在 `savedInstanceState == null` 時（首次啟動）將 `LedgerListFragment` 放進 `R.id.container`，且不將初始頁面放入 Back Stack，確保旋轉時能由 `FragmentManager` 正確還原現有 Fragment。

### 四、清單頁面 (`LedgerListFragment.kt` & `fragment_ledger_list.xml`)
* 檔案位置：
  * [fragment_ledger_list.xml](file:///D:/android%20studio/MyApplication2/app/src/main/res/layout/fragment_ledger_list.xml)
  * [LedgerListFragment.kt](file:///D:/android%20studio/MyApplication2/app/src/main/java/com/example/myapplication/LedgerListFragment.kt)
* 實作說明：包含三個按鈕 `btnLunch` ("午餐 120")、`btnMetro` ("捷運 35")、`btnBook` ("書籍 450")。點擊後呼叫 `LedgerDetailFragment.newInstance(title, amount)`，透過 `parentFragmentManager.beginTransaction()` 進行 `replace()`，並搭配 `setReorderingAllowed(true)` 與 `addToBackStack(null)`。

### 五、明細頁面 (`LedgerDetailFragment.kt` & `fragment_ledger_detail.xml`)
* 檔案位置：
  * [fragment_ledger_detail.xml](file:///D:/android%20studio/MyApplication2/app/src/main/res/layout/fragment_ledger_detail.xml)
  * [LedgerDetailFragment.kt](file:///D:/android%20studio/MyApplication2/app/src/main/java/com/example/myapplication/LedgerDetailFragment.kt)
* 實作說明：提供 `companion object` 靜態工廠方法 `newInstance(title, amount)`，將品名與金額放入 `arguments` Bundle（使用固定 Key）。保留無參數預設建構子，並在 `onViewCreated()` 讀取 `arguments` 顯示於 `tvTitle` 與 `tvAmount`。螢幕旋轉後資料不丟失。

### 六、ViewBinding 生命週期管理
* 在 `LedgerListFragment` 與 `LedgerDetailFragment` 中均宣告 `private var _binding: ...? = null` 與 `private val binding get() = _binding!!`。
* 在 `onCreateView()` 進行 `inflate()`。
* 在 `onDestroyView()` 呼叫 `super.onDestroyView()` 並將 `_binding = null`，防止記憶體洩漏與非法存取。

---

## 🧪 驗證結果說明

### 1. 靜態檢查與編譯測試
* **Gradle Build (`:app:assembleDebug`)**：編譯成功 (`Build finished successfully`)，產出 `app-debug.apk`。
* **單元測試 (`:app:testDebugUnitTest`)**：測試通過 (1 passed, 0 failed)。

### 2. 未完成之手動驗證項目說明
* 由於目前執行環境未安裝 Android 模擬器或連接實體 Android 裝置，未能執行動態畫面旋轉測試。已通過全數語法與生命週期規範靜態檢查，請參考下方手動測試步驟於模擬器實測。

---

## 📱 手動測試步驟 (Manual Testing Steps)
1. **啟動 App**：確認首次啟動僅顯示清單頁面，顯示「午餐 120」、「捷運 35」、「書籍 450」三個按鈕。
2. **進入明細**：分別點擊三個按鈕，確認開啟明細頁，標題與金額正確顯示（例：點擊「午餐 120」，明細顯示「午餐」與「金額：120 元」）。
3. **返回清單**：按系統返回鍵，確認順利返回清單頁面。
4. **旋轉測試（清單頁）**：在清單頁進行多次螢幕旋轉（直向↔橫向），確認清單畫面正常，無重複疊加或崩潰。
5. **旋轉測試（明細頁）**：進入任一帳目明細頁後進行多次旋轉，確認品名與金額維持顯示，頁面停留在明細，且按返回鍵仍能正確返回清單頁。
6. **重複操作**：多次進入明細與返回清單，確認無 ViewBinding 相關 Crash。
