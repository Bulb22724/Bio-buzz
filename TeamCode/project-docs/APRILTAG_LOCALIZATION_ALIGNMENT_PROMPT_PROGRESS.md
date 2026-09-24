# AprilTag Localization and Alignment Prompt Progress

This branch-local record is the source of truth for Stage 5 student handoff. Do not use chat
history to infer completion.

Working branch: `codex/Vision`

## Status meanings

- `Not started`: no work has run on this branch.
- `Results ready`: evidence exists but the student has not finished review.
- `Reviewed`: the student accepted the evidence and resolved every decision required next.
- `Blocked`: a prerequisite, safety condition, build, permission, or required decision is open.

## Rules

- Start Here confirms the current checked-out branch; it never creates or switches branches.
- Execute only the first prompt not marked `Reviewed`.
- Immediately before presenting results, change only that prompt row to `Results ready`, or to
  `Blocked` with the exact reason.
- Mark `Reviewed` only in a later turn after the student attempts five new learning questions,
  resolves required engineering decisions, and explicitly accepts the result.
- Cite repository files, command results, measurement records, or current official sources. Do not
  record quiz answers, scores, credentials, serial numbers, or personal information.

| Prompt | Status | Review date | Durable evidence or decision |
| --- | --- | --- | --- |
| LA-01 | Reviewed | 2026-09-24 | Student accepted the repository/SDK 11.2.1 and official-guidance evidence. Stage 5 will use fixed DECODE GOAL tags 20 and 24 through a replaceable season configuration, the official FTC field coordinate system, and independent fresh field-pose candidates rather than a maintained estimate. Candidates require a `FRESH` snapshot, age at most 250 ms, verified fixed-tag metadata, matching calibration, verified mount, and finite pose; failures expose a rejection reason. Pedro/odometry fusion, BIOBUZZ SDK 12 migration, alignment control, and autonomous integration remain deferred. DECODE tags 21-23 and moving BIOBUZZ tags are not absolute-localization references. No production code changed; JDK 17 baseline passed. |
| LA-02 | Reviewed | 2026-09-24 | Student accepted the Stage 5 design recorded in `APRILTAG_VISION_ARCHITECTURE_DECISION.md`: official FTC field coordinates; replaceable DECODE fixed-GOAL tag 20/24 configuration; SDK 11.2.1 `setCameraPose`/`robotPose` below hardware; optional candidate plus separate status on each immutable observation; and neutral `FieldPose`, `AprilTagFieldPoseCandidate`, and `AprilTagLocalizationConfiguration` classes. The unchanged forward camera remains neutral mount rotation 0/0/0 and maps to SDK camera yaw 0, pitch -90, roll 0. Candidate gates produce an accepted value or explicit rejection. Pedro, BIOBUZZ SDK 12, fusion, alignment, autonomous, Limelight, and motors remain deferred. Builds and diff check passed. |
| LA-03 | Reviewed | 2026-09-24 | Student accepted the neutral immutable `FieldPose`, `AprilTagFieldPoseCandidate`, and defensive `AprilTagLocalizationConfiguration` types. Checked-out SDK 11.2.1 bytecode verified fixed 6.5-inch `BlueTarget` tag 20 at (-58.3727, -55.6425, 29.5) inches and `RedTarget` tag 24 at (-58.3727, 55.6425, 29.5), each with field orientation; OBELISK tags 21-23 have no field pose. SDK metadata remains authoritative without duplicated TeamCode coordinates/quaternions; only tags 20/24 may produce candidates. Constructor/immutability checks, JDK 17 build, prohibited-dependency search, and diff check passed. Live candidate wiring remains for LA-04. |
| LA-04 | Not started | — | — |
| LA-05 | Not started | — | — |
| LA-06 | Not started | — | — |
| LA-07 | Not started | — | — |
| LA-08 | Not started | — | — |
| LA-09 | Not started | — | — |
| LA-10 | Not started | — | — |
