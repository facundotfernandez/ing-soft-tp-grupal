/** @type {import('tailwindcss').Config} */

import colors from "tailwindcss/colors";

module.exports = {
    darkMode: 'class',
    content: ["./src/pages/**/*.{js,ts,jsx,tsx,mdx}", "./src/components/**/*.{js,ts,jsx,tsx,mdx}", "./node_modules/@tremor/**/*.{js,ts,jsx,tsx}",],
    theme: {
        transparent: 'transparent',
        current: 'currentColor',
        screens: {
            // Minimum screen sizes
            'min-sm': '640px',
            'min-md': '768px',
            'min-lg': '1024px',
            'min-xl': '1280px',
            'min-2xl': '1536px',

            // Maximum screen sizes
            'max-xs': {'max': '639px'},
            'max-sm': {'max': '767px'},
            'max-md': {'max': '1023px'},
            'max-lg': {'max': '1279px'},
            'max-xl': {'max': '1535px'},
            'max-2xl': {'max': '1919px'},

            // Range between sizes
            'sm-md': {
                'min': '640px',
                'max': '767px'
            },
            'md-lg': {
                'min': '768px',
                'max': '1023px'
            },
            'lg-xl': {
                'min': '1024px',
                'max': '1279px'
            },
            'xl-2xl': {
                'min': '1280px',
                'max': '1535px'
            },

            // Default screen sizes
            'xs': '480px',
            'sm': '640px',
            'md': '768px',
            'lg': '1024px',
            'xl': '1280px',
            '2xl': '1536px',
        },
        extend: {
            colors: {
                tremor: {
                    brand: {
                        faint: colors.blue[50],
                        muted: colors.blue[200],
                        subtle: colors.blue[400],
                        DEFAULT: colors.blue[500],
                        emphasis: colors.blue[700],
                        inverted: colors.white,
                    },
                    background: {
                        muted: colors.gray[50],
                        subtle: colors.gray[100],
                        DEFAULT: colors.white,
                        emphasis: colors.gray[700],
                    },
                    border: {
                        DEFAULT: colors.gray[200],
                    },
                    ring: {
                        DEFAULT: colors.gray[200],
                    },
                    content: {
                        subtle: colors.gray[400],
                        DEFAULT: colors.gray[500],
                        emphasis: colors.gray[700],
                        strong: colors.gray[900],
                        inverted: colors.white,
                    },
                }, // dark mode
                'dark-tremor': {
                    brand: {
                        faint: '#0B1229',
                        muted: colors.blue[950],
                        subtle: colors.blue[800],
                        DEFAULT: colors.blue[500],
                        emphasis: colors.blue[400],
                        inverted: colors.blue[950],
                    },
                    background: {
                        muted: '#131A2B',
                        subtle: colors.gray[800],
                        DEFAULT: colors.gray[900],
                        emphasis: colors.gray[300],
                    },
                    border: {
                        DEFAULT: colors.gray[800],
                    },
                    ring: {
                        DEFAULT: colors.gray[800],
                    },
                    content: {
                        subtle: colors.gray[600],
                        DEFAULT: colors.gray[500],
                        emphasis: colors.gray[200],
                        strong: colors.gray[50],
                        inverted: colors.gray[950],
                    },
                },
                matisse: {
                    '50': '#f0f8ff',
                    '100': '#e0f0fe',
                    '200': '#bae1fd',
                    '300': '#7ccafd',
                    '400': '#37aff9',
                    '500': '#0d95ea',
                    '600': '#016eba',
                    '700': '#025ea2',
                    '800': '#065086',
                    '900': '#0c436e',
                    '950': '#082a49',
                },
                'background-light': '#ffffff',
                'surface-light': '#f5f5f5',
                'text-primary-light': '#333333',
                'text-secondary-light': '#082a49',
                'border-light': '#e0e0e0',

                'background-dark': '#00223b',
                'surface-dark': '#1e1e1e',
                'text-primary-dark': '#e0e0e0',
                'text-secondary-dark': '#e0f0fe',
                'border-dark': '#333333',
            },
            boxShadow: {
                // light
                'tremor-input': '0 1px 2px 0 rgb(0 0 0 / 0.05)',
                'tremor-card': '0 1px 3px 0 rgb(0 0 0 / 0.1), 0 1px 2px -1px rgb(0 0 0 / 0.1)',
                'tremor-dropdown': '0 4px 6px -1px rgb(0 0 0 / 0.1), 0 2px 4px -2px rgb(0 0 0 / 0.1)', // dark
                'dark-tremor-input': '0 1px 2px 0 rgb(0 0 0 / 0.05)',
                'dark-tremor-card': '0 1px 3px 0 rgb(0 0 0 / 0.1), 0 1px 2px -1px rgb(0 0 0 / 0.1)',
                'dark-tremor-dropdown': '0 4px 6px -1px rgb(0 0 0 / 0.1), 0 2px 4px -2px rgb(0 0 0 / 0.1)',
            },
            borderRadius: {
                'tremor-small': '0.375rem',
                'tremor-default': '0.5rem',
                'tremor-full': '9999px',
            },
            fontSize: {
                'tremor-label': ['0.75rem', {lineHeight: '1rem'}],
                'tremor-default': ['0.875rem', {lineHeight: '1.25rem'}],
                'tremor-title': ['1.125rem', {lineHeight: '1.75rem'}],
                'tremor-metric': ['1.875rem', {lineHeight: '2.25rem'}],
            },
            backgroundImage: {
                "gradient-radial": "radial-gradient(var(--tw-gradient-stops))",
                "gradient-conic": "conic-gradient(from 180deg at 50% 50%, var(--tw-gradient-stops))",
            },
            fontFamily: {
                sans: ['Montserrat', 'Inter', 'system-ui', 'Avenir', 'Helvetica', 'Arial', 'sans-serif'],
            },
            animation: {
                'loading-wave': 'loading-wave-animation 1s ease-in-out infinite',
            },
            keyframes: {
                'loading-wave-animation': {
                    '0%, 100%': {height: '10px'},
                    '50%': {height: '50px'},
                },
                hide: {
                    from: {opacity: "1"},
                    to: {opacity: "0"},
                },
                slideDownAndFade: {
                    from: {
                        opacity: "0",
                        transform: "translateY(-6px)"
                    },
                    to: {
                        opacity: "1",
                        transform: "translateY(0)"
                    },
                },
                slideLeftAndFade: {
                    from: {
                        opacity: "0",
                        transform: "translateX(6px)"
                    },
                    to: {
                        opacity: "1",
                        transform: "translateX(0)"
                    },
                },
                slideUpAndFade: {
                    from: {
                        opacity: "0",
                        transform: "translateY(6px)"
                    },
                    to: {
                        opacity: "1",
                        transform: "translateY(0)"
                    },
                },
                slideRightAndFade: {
                    from: {
                        opacity: "0",
                        transform: "translateX(-6px)"
                    },
                    to: {
                        opacity: "1",
                        transform: "translateX(0)"
                    },
                },
                accordionOpen: {
                    from: {height: "0px"},
                    to: {height: "var(--radix-accordion-content-height)"},
                },
                accordionClose: {
                    from: {
                        height: "var(--radix-accordion-content-height)",
                    },
                    to: {height: "0px"},
                },
                dialogOverlayShow: {
                    from: {opacity: "0"},
                    to: {opacity: "1"},
                },
                dialogContentShow: {
                    from: {
                        opacity: "0",
                        transform: "translate(-50%, -45%) scale(0.95)",
                    },
                    to: {
                        opacity: "1",
                        transform: "translate(-50%, -50%) scale(1)"
                    },
                },
                animation: {
                    hide: "hide 150ms cubic-bezier(0.16, 1, 0.3, 1)",
                    slideDownAndFade: "slideDownAndFade 150ms cubic-bezier(0.16, 1, 0.3, 1)",
                    slideLeftAndFade: "slideLeftAndFade 150ms cubic-bezier(0.16, 1, 0.3, 1)",
                    slideUpAndFade: "slideUpAndFade 150ms cubic-bezier(0.16, 1, 0.3, 1)",
                    slideRightAndFade: "slideRightAndFade 150ms cubic-bezier(0.16, 1, 0.3, 1)", // Accordion
                    accordionOpen: "accordionOpen 150ms cubic-bezier(0.87, 0, 0.13, 1)",
                    accordionClose: "accordionClose 150ms cubic-bezier(0.87, 0, 0.13, 1)", // Dialog
                    dialogOverlayShow: "dialogOverlayShow 150ms cubic-bezier(0.16, 1, 0.3, 1)",
                    dialogContentShow: "dialogContentShow 150ms cubic-bezier(0.16, 1, 0.3, 1)",
                },

            },
        },
    },
    plugins: [require('@headlessui/tailwindcss'), require('@tailwindcss/forms'), require('@tailwindcss/aspect-ratio')],
};
