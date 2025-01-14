# Color Accessibility Documentation

This document covers the accessibility aspects of our color palette, focusing on **contrast ratios** and **color-blind safety** using Adobe's tools.

---

## Tools Overview

### **Adobe Color Blind Safe Checker**
- This tool ensures that the selected colors are distinguishable by individuals with various types of color blindness:
    - **Deuteranopia**: Red-green color blindness.
    - **Protanopia**: Reduced sensitivity to red light.
    - **Tritanopia**: Blue-yellow color blindness.
- Results: **No conflicts found** — our palette is color-blind safe.

### **Adobe Contrast Checker**
- This tool measures the contrast ratio between text and background colors to meet WCAG (Web Content Accessibility Guidelines) standards:
    - **Normal Text (17pt or smaller)**: Requires a contrast ratio of at least **4.5:1**.
    - **Large Text (18pt or 14pt bold)**: Requires a contrast ratio of at least **3:1**.
    - **UI Components and Graphics**: Requires a contrast ratio of at least **3:1**.

---

## Color Palette

Our primary and secondary colors are designed to ensure accessibility while maintaining a cohesive visual style.

### **Primary Colors**
```css
--dark-primary: #212D39;           /* Main dark background */
--dark-secondary: #24507B;        /* Secondary dark accent */
--light-primary: #ECECEC;         /* Light text or background color */
```

### **Button Colors**
```css
--button-important: #E74C3C;      /* Important button color */
--button-important-hover: #800000; /* Hover state for important buttons */
```

---

## Accessibility Analysis

### **Color Blind Safe Checker**
Our palette has been tested for all major types of color blindness.
- **Result**: No conflicts found.  
  All colors remain distinguishable under the following simulated conditions:
    - **Deuteranopia**
    - **Protanopia**
    - **Tritanopia**

### **Contrast Checker**

#### **Light Primary Text (`#ECECEC`) on Button Important (`#E74C3C`)**
- **Contrast Ratio**: 3.23:1
- **Evaluation**:
    - Fails for normal-sized text (17pt or smaller).
    - Passes for large text (18pt and above or 14pt bold and above).
- **Recommendation**: Consider darkening the text color slightly or lightening the button background to improve readability.

#### **Dark Primary (`#212D39`) on Dark Secondary (`#24507B`)**
- **Contrast Ratio**: Not suitable for small text.
- Best used for graphical components or subtle UI elements. Avoid pairing these colors for body text.

#### **Button Important Hover (`#800000`)**
- Text on this hover color must be tested with the intended foreground color. Ensure it maintains at least **3:1** contrast for large text or graphics.

---

## WCAG Compliance Guidelines

- **Normal Text**: 4.5:1 or higher.
- **Large Text (18pt+ or 14pt bold)**: 3:1 or higher.
- **Graphics and UI Components**: 3:1 or higher.

---

## Recommendations for Improvement

1. **Enhance Button Contrast**:
    - Adjust `#E74C3C` to improve contrast with `#ECECEC` for smaller text.
    - For example, a darker red (e.g., `#D8342A`) can help meet the required contrast ratio.

2. **Test Hover States**:
    - Verify text color contrast on `button-important-hover` (`#800000`) to maintain accessibility compliance.

3. **Color Pairing for Text**:
    - Avoid using **dark-primary** (`#212D39`) and **dark-secondary** (`#24507B`) together for critical text.
    - Reserve this combination for less prominent UI elements or graphical decorations.

4. **Consistency**:
    - Ensure the primary and secondary colors are used consistently to create a recognizable theme throughout the design.


## Result
<br>
We already follow all the guidelines for color accessibility. However, we will make minor adjustments to improve the contrast ratio for smaller text on important buttons. This will ensure that our application remains accessible to all users.
---

## Usage Guidelines

1. **Button Colors**:
    - Use `button-important` (`#E74C3C`) for primary calls-to-action (CTAs) requiring attention.
    - Implement `button-important-hover` for interactive hover effects.

2. **Text on Dark Backgrounds**:
    - Always use `light-primary` (`#ECECEC`) for text on `dark-primary` or `dark-secondary` backgrounds to maintain readability.

3. **Text Size**:
    - For normal text (e.g., paragraph text), ensure contrast ratios meet **4.5:1**.
    - Use large text (18pt or larger) or bold text (14pt bold) where the contrast ratio is lower.

4. **Testing During Development**:
    - Continuously use Adobe’s contrast checker to verify compliance as you iterate on designs.
    - Test all interactive states, such as hover, active, and focus states, to ensure accessibility in every scenario.

---

## Accessibility Standards

Our goal is to meet or exceed **WCAG AA** guidelines, ensuring our application is inclusive and accessible to as many users as possible. By adhering to these recommendations, we can create a visually appealing design that remains functional and accessible to all.

---
