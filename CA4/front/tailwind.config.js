module.exports = {
  mode: "jit",
  content: ["./src/**/**/*.{js,ts,jsx,tsx,html,mdx}", "./src/**/*.{js,ts,jsx,tsx,html,mdx}"],
  darkMode: "class",
  theme: {
    screens: { md: { max: "1050px" }, sm: { max: "550px" } },
    extend: {
      colors: {
        white: { A700: "#ffffff" },
        blue_gray: { 100: "#d9d9d9", 900: "#2f2f2f" },
        gray: { 100: "#f5f5f5", 600: "#7e7e7e" },
        red: { 600: "#ed3545" },
      },
      boxShadow: {},
      fontFamily: { splinesans: "Spline Sans" },
    },
  },
  plugins: [],
};
