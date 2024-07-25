import static layout.Stylization.displayColorCommand;
import static layout.Stylization.drawFullTripleBorder;

public class Eula {
    public static void displayEula() {
        drawFullTripleBorder();
        displayColorCommand(
                "End User License Agreement (EULA) for Using Free Software in Non-Commercial Purposes\n\n" +
                        "Introduction\n" +
                        "This End User License Agreement (hereinafter referred to as \"Agreement\") governs the terms of use for the free software known as MultiCLIA\n(hereinafter referred to as \"Software\"), developed by [Nick Zozulia[EN] / Mykyta Zozulia[UA] ] (hereinafter referred to as \"Developer\"). By using the Software, you agree to the terms of this Agreement.\n\n" +

                        "1. Grant of License\n" +
                        "1.1. The Developer grants you a limited, non-exclusive, non-transferable license to use the Software for non-commercial purposes only.\n" +
                        "1.2. The Software is provided \"as is\" without any warranties, express or implied.\n\n" +

                        "2. Restrictions\n" +
                        "2.1. You may not use the Software for commercial purposes, including but not limited to selling, leasing, or providing it under a commercial subscription.\n" +
                        "2.2. You may not modify, decompile, disassemble, or otherwise attempt to derive the source code of the Software, except as permitted by applicable law.\n" +
                        "2.3. You may not distribute the Software except as expressly permitted by this Agreement or with written permission from the Developer.\n\n" +

                        "3. User Rights and Obligations\n" +
                        "3.1. You agree to use the Software in accordance with the terms of this Agreement and applicable laws.\n" +
                        "3.2. You agree not to use the Software for any unlawful purposes or in any manner that could harm third parties.\n\n" +

                        "4. Ownership and Contributions\n" +
                        "4.1. All rights, title, and interest in and to the Software, including any updates or modifications, are and will remain the exclusive property of the Developer.\n" +
                        "4.2. The Software is open-source, and users are allowed to create their own versions based on the provided resources.\nUsers can also integrate additional functionalities from the Developer's GitHub repository, such as console games or health programs, provided these integrations are for non-commercial purposes.\n" +
                        "4.3. Users are permitted to write, create, and enhance the Software, but all modifications and derivatives must be clearly attributed to the original Software and Developer.\n\n" +

                        "5. Privacy\n" +
                        "5.1. The Software does not collect any user data. The Developer respects user privacy and does not implement any data collection mechanisms within the Software.\n\n" +

                        "6. Limitation of Liability\n" +
                        "6.1. The Developer shall not be liable for any direct, indirect, incidental, special, or consequential damages,\nincluding but not limited to loss of profits, data, or business interruption, arising out of the use or inability to use the Software, even if the Developer has been advised of the possibility of such damages.\n" +
                        "6.2. The Developer does not warrant that the Software will meet your requirements, operate uninterrupted, or be error-free.\n\n" +

                        "7. Termination\n" +
                        "7.1. This Agreement is effective from the moment you install or use the Software and remains in effect until terminated.\n" +
                        "7.2. You may terminate this Agreement at any time by ceasing all use of the Software and deleting all copies.\n" +
                        "7.3. The Developer may terminate this Agreement if you violate its terms, requiring you to cease all use of the Software and delete all copies.\n\n" +

                        "8. Miscellaneous\n" +
                        "8.1. If any provision of this Agreement is found to be invalid or unenforceable, the remaining provisions will remain in full force and effect.\n\n" +

                        "9. Contact Information\n" +
                        "If you have any questions about this Agreement, please contact the Developer at: [Github: Akillot].\n\n" +

                        "Conclusion\n" +
                        "By using the Software, you agree to the terms of this Agreement. If you do not agree to the terms of this Agreement, you may not use the Software.\n", "white", (byte) 0);
        drawFullTripleBorder();
    }
}
