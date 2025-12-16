"use client";

import { AppNavbar } from "@/components/app-navbar";
import { SidebarNav } from "@/components/sidebar-nav";
import { Home, FileText, Upload, CheckSquare, PackageCheck } from "lucide-react";

export default function CaseLayout({
    children,
    params
}: {
    children: React.ReactNode;
    params: { id: string };
}) {
    const caseId = params.id;

    const sidebarNavItems = [
        {
            title: "Overview",
            href: `/cases/${caseId}`,
            icon: Home,
        },
        {
            title: "Questionnaire",
            href: `/cases/${caseId}/questionnaire`,
            icon: FileText,
        },
        {
            title: "Documents",
            href: `/cases/${caseId}/documents`,
            icon: Upload,
        },
        {
            title: "Validation",
            href: `/cases/${caseId}/validation`,
            icon: CheckSquare,
        },
        {
            title: "Package",
            href: `/cases/${caseId}/package`,
            icon: PackageCheck,
        },
    ];

    return (
        <div className="flex flex-col min-h-screen">
            <AppNavbar />
            <div className="flex-1 container grid flex-1 gap-12 md:grid-cols-[200px_1fr] lg:grid-cols-[250px_1fr] py-8">
                <aside className="hidden w-[200px] flex-col md:flex lg:w-[250px]">
                    <SidebarNav items={sidebarNavItems} />
                </aside>
                <main className="flex w-full flex-1 flex-col overflow-hidden">
                    {children}
                </main>
            </div>
        </div>
    );
}
