"use client";

import Link from "next/link";
import { useSession, signIn, signOut } from "next-auth/react";
import { Button } from "@/components/ui/button";

export function AppNavbar() {
    const { data: session } = useSession();

    return (
        <nav className="border-b bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/60">
            <div className="container flex h-14 items-center">
                <div className="mr-4 hidden md:flex">
                    <Link href="/" className="mr-6 flex items-center space-x-2">
                        <span className="hidden font-bold sm:inline-block">VisaFlow</span>
                    </Link>
                    <nav className="flex items-center space-x-6 text-sm font-medium">
                        <Link href="/dashboard" className="transition-colors hover:text-foreground/80 text-foreground/60 leading-6">Dashboard</Link>
                    </nav>
                </div>
                <div className="flex flex-1 items-center justify-between space-x-2 md:justify-end">
                    {session ? (
                        <div className="flex items-center gap-4">
                            <span className="text-sm text-muted-foreground">{session.user?.name}</span>
                            <Button variant="ghost" onClick={() => signOut()}>Log out</Button>
                        </div>
                    ) : (
                        <Button onClick={() => signIn("keycloak")}>Sign In</Button>
                    )}
                </div>
            </div>
        </nav>
    );
}
