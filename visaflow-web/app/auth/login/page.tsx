"use client";

import { useSession, signIn } from "next-auth/react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { AppNavbar } from "@/components/app-navbar";
import { useRouter } from "next/navigation";
import { useEffect } from "react";

export default function LoginPage() {
    const { data: session } = useSession();
    const router = useRouter();

    useEffect(() => {
        if (session) {
            router.push("/dashboard");
        }
    }, [session, router]);

    return (
        <div className="flex flex-col min-h-screen">
            <AppNavbar />
            <div className="flex-1 flex items-center justify-center">
                <Card className="w-[350px]">
                    <CardHeader>
                        <CardTitle>Welcome back</CardTitle>
                        <CardDescription>Sign in to your VisaFlow account</CardDescription>
                    </CardHeader>
                    <CardContent>
                        <Button className="w-full" onClick={() => signIn("keycloak", { callbackUrl: "/dashboard" })}>
                            Sign In with Keycloak
                        </Button>
                    </CardContent>
                </Card>
            </div>
        </div>
    );
}
