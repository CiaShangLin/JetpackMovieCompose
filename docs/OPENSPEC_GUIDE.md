# OpenSpec 使用指南

本專案已整合 [OpenSpec](https://github.com/Fission-AI/OpenSpec)，一套「先寫規格、再寫程式」的 AI 協作工作流程（commit `afecfff`）。CLI 已全域安裝（`@fission-ai/openspec@1.2.0`）。

## OpenSpec 是什麼

核心概念是把一個變更（change）拆成幾個產出物（artifact）：

- `proposal.md`：為什麼做（what & why）
- `design.md`：怎麼做（how）
- `tasks.md`：拆解成任務清單

AI 依照任務清單逐一實作，完成後把規格差異（delta specs）同步進主規格、並封存這次變更。

## 專案目前的設置

```
openspec/
├── config.yaml       # schema: spec-driven，可加專案背景/自訂規則（目前空白）
├── changes/
│   └── archive/      # 已封存的變更（目前是空的）
└── specs/            # 正式規格（目前是空的）
```

`config.yaml` 有兩個可選欄位還沒填，之後可視需要補上：

- `context`：給 AI 看的專案背景（技術棧、慣例等），可以填入本 repo 的 Kotlin / Compose / Hilt 慣例
- `rules`：針對特定 artifact 的自訂規則（例如「proposal 不超過 500 字」）

## 四個指令（對應四個 skill）

| 指令 | 用途 | 對應 skill |
|---|---|---|
| `/opsx:explore [主題]` | **思考階段**，只探索不實作，可以看程式碼、畫 ASCII 圖、比較方案，也可以順手建立 proposal/design 草稿。**絕不寫程式碼** | `openspec-explore` |
| `/opsx:propose [描述或名稱]` | 建立新變更，一次生成 `proposal.md` → `design.md` → `tasks.md` | `openspec-propose` |
| `/opsx:apply [變更名稱]` | 讀取 `tasks.md`，逐一實作任務，完成一項就把 `- [ ]` 改成 `- [x]` | `openspec-apply-change` |
| `/opsx:archive [變更名稱]` | 檢查任務/artifact 是否完成 → 詢問是否同步 delta specs 到主規格 → 搬到 `openspec/changes/archive/YYYY-MM-DD-<name>/` | `openspec-archive-change` |

指令定義位於 `.claude/commands/opsx/`，skill 定義位於 `.claude/skills/openspec-*/SKILL.md`。

## 典型流程

```
/opsx:explore "想幫收藏功能加上分類標籤"
        ↓（想法成熟後）
/opsx:propose add-collection-tags
        → 產生 openspec/changes/add-collection-tags/{proposal,design,tasks}.md
        ↓
/opsx:apply add-collection-tags
        → 逐一實作 tasks.md 裡的項目，程式碼真的被改動
        ↓
/opsx:archive add-collection-tags
        → 確認完成 → 同步 spec → 搬進 archive/
```

## 使用細節

- `/opsx:apply` 若省略名稱，會嘗試從對話推斷，或在只有一個進行中變更時自動選取；多個變更存在時會用 `AskUserQuestion` 讓使用者選擇
- `/opsx:archive` 遇到未完成的任務或 artifact 不會擋，只會警告後讓使用者確認是否仍要封存
- explore 模式明確被設計成「不寫程式碼」，適合先釐清需求再走 propose

## 底層 CLI

指令本身是包裝以下 CLI：

```bash
openspec list --json              # 列出目前所有變更狀態
openspec status --change <name>   # 查看某變更的 artifact 完成度
openspec new change "<name>"      # 手動建立變更骨架
openspec view                     # 互動式 dashboard
```

## 目前狀態

`openspec/changes/` 與 `openspec/specs/` 目前都是空的，代表工作流程已裝好但尚未開始使用。可先執行 `/opsx:explore` 或 `/opsx:propose` 描述一個想做的小改動，體驗一輪完整流程。
